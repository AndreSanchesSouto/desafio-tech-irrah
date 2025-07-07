package com.irrah.back_end.services;

import com.irrah.back_end.dtos.message.RequestMessageDto;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.entities.ChatEntity;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.MessagePriority;
import com.irrah.back_end.enums.MessageStatus;
import com.irrah.back_end.enums.PlanType;
import com.irrah.back_end.exceptions.MessageException;
import com.irrah.back_end.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    @Lazy
    private OrchestratorService orchestratorService;

    public MessageEntity getLastMessageFromUser(UUID userId) {
        return this.repository.findLastMessageFromUser(userId);
    }

    public ResponseMessageDto post(RequestMessageDto request) {
        ChatEntity chat = this.orchestratorService.chatGetById(request.conversationId());
        UserEntity sender = this.orchestratorService.findCurrentUser();
        UserEntity receiver = selectReceiver(chat, sender.getId());
        MessageEntity message = createBaseMessage(request, sender, receiver);

        if (sender.getPlanType().equals(PlanType.PREPAID.getPlanType())) {
            handlePrepaidMessage(sender, message);
        } else {
            handlePostpaidMessage(sender, message);
        }
        message.setChat(chat);
        this.repository.save(message);
        this.orchestratorService.chatSetNewMassage(chat.getId(), message);
        return new ResponseMessageDto(message);
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
        message.setType(request.userPlanType().getPlanType());
        message.setUserReceiver(receiver);
        message.setUserSender(sender);

        return message;
    }

    private void handlePrepaidMessage(UserEntity user, MessageEntity message) {
        if (this.orchestratorService.userCanPayMessageCost(user, message)) {
            message.setStatus(MessageStatus.QUEUED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }

    private void handlePostpaidMessage(UserEntity user, MessageEntity message) {
        if (this.orchestratorService.userHasMonthLimit(user, message)) {
            message.setStatus(MessageStatus.QUEUED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }
}
