package com.irrah.back_end.services;

import com.irrah.back_end.dtos.message.RequestMessageDto;
import com.irrah.back_end.dtos.message.RequestMessagesToCheckDto;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.entities.ChatEntity;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.MessagePriority;
import com.irrah.back_end.enums.MessageStatus;
import com.irrah.back_end.enums.PlanType;
import com.irrah.back_end.exceptions.MessageException;
import com.irrah.back_end.exceptions.QueueException;
import com.irrah.back_end.queue.MessageQueue;
import com.irrah.back_end.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    @Lazy
    private OrchestratorService orchestratorService;

    @Autowired
    private MessageQueue messageQueue;


    public MessageEntity getLastMessageFromUser(UUID userId) {
        return this.repository.findLastMessageFromUser(userId);
    }

    public ResponseMessageDto post(RequestMessageDto request) {
        ChatEntity chat = this.orchestratorService.chatGetById(request.conversationId());
        UserEntity sender = this.orchestratorService.findCurrentUser();
        UserEntity receiver = selectReceiver(chat, sender.getId());
        MessageEntity message = createBaseMessage(request, sender, receiver);

        message.setStatus(MessageStatus.QUEUED.getStatus());
        message.setChat(chat);
        this.repository.save(message);
        this.orchestratorService.chatSetNewMassage(chat.getId(), message);
        this.enqueueMessage(message);
        return new ResponseMessageDto(message);
    }

    public void patchMessageStatus(MessageEntity message, String status) {
        message.setStatus(MessageStatus.valueOf(status.toUpperCase()).getStatus());
        this.repository.save(message);
    }

    public MessageEntity findById(UUID id) {
        if(id == null) throw new MessageException("Id deve ser válido");
        return this.repository.findById(id).orElseThrow(
                () -> new MessageException("Mensagem não encontradaa")
        );
    }

    private void enqueueMessage(MessageEntity message) {
        try {
            messageQueue.enqueue(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new QueueException("Erro ao enfileirar mensagem " + e);
        }
    }

    private UserEntity selectReceiver(ChatEntity chat, UUID senderId) {
        if(chat.getUserCommon().getId().equals(senderId)) return chat.getUserAdminer();
        if(chat.getUserAdminer().getId().equals(senderId)) return chat.getUserCommon();
        throw new MessageException("Remetende desconhecido");
    }

    private MessageEntity createBaseMessage(RequestMessageDto request, UserEntity sender, UserEntity receiver) {
        MessagePriority priority = MessagePriority.fromType(request.priority());

        MessageEntity message = new MessageEntity();
        message.setPriorityLevel(priority.getType());
        message.setPrice(priority.getPrice());
        message.setText(request.content());
        message.setType(this.orchestratorService.findCurrentUser().getPlanType());
        message.setUserReceiver(receiver);
        message.setUserSender(sender);

        return message;
    }

    public void handlePaymentMessage(UserEntity user, MessageEntity message) {
        if (this.orchestratorService.userCanPayMessageCost(user, message)) {
            message.setStatus(MessageStatus.DELIVERED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }

    public List<ResponseMessageDto> getMessages(UUID chatId) {
        return this.findByChatId(chatId);
    }

    private List<ResponseMessageDto> findByChatId(UUID chatId) {
        List<MessageEntity> messages =  this.repository.findByChatId(chatId);
        return messages
                .stream()
                .map(ResponseMessageDto::new)
                .toList();
    }

    public List<ResponseMessageDto> markAsRead(RequestMessagesToCheckDto messages) {
        List<MessageEntity> messagesList = new LinkedList<MessageEntity>();
        for(UUID id : messages.messagesId()) {
            MessageEntity messageEntity = this.findById(id);
            messageEntity.setStatus(MessageStatus.READ.getStatus());
            this.repository.save(messageEntity);
            messagesList.add(messageEntity);
            this.enqueueMessage(messageEntity);

        }
        return messagesList
                .stream()
                .map(ResponseMessageDto::new)
                .toList();
    }
}
