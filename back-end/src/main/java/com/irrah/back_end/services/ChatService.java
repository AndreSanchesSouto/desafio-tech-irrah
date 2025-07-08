package com.irrah.back_end.services;

import com.irrah.back_end.dtos.chat.RequestChatDto;
import com.irrah.back_end.dtos.chat.ResponseChatDto;
import com.irrah.back_end.dtos.chat.ResponseGenericChatDto;
import com.irrah.back_end.entities.ChatEntity;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.Role;
import com.irrah.back_end.exceptions.ChatException;
import com.irrah.back_end.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository repository;

    @Autowired
    @Lazy
    private OrchestratorService orchestratorService;

    public List<ResponseGenericChatDto> getChats() {
        List<ChatEntity> chats = repository.findAll();
        List<ResponseGenericChatDto> dto = new ArrayList<ResponseGenericChatDto>();
        for(ChatEntity chat : chats) {
           dto.add(this.chatEntityParseResponseGeneric(chat));
        }
        return dto;
    }

    public ResponseGenericChatDto getCommonChat() {
        UserEntity user = orchestratorService.findCurrentUser();
        ChatEntity chat = this.findByUserId(user.getId());
        return this.chatEntityParseResponseGeneric(chat);
    }

    public ResponseGenericChatDto chatEntityParseResponseGeneric(ChatEntity chatEntity) {
        if (chatEntity == null) {
            return createDefaultResponse();
        }

        if (chatEntity.getUserCommon().getRole().equals(Role.ADMINER.getRole())) {
            return null;
        }

        UUID id = chatEntity.getId();
        UserEntity userCommon = chatEntity.getUserCommon();
        UUID commonId = userCommon.getId();
        String commonName = userCommon.getName();

        String text = null;
        String timer = null;
        int messageCount = 0;

        List<MessageEntity> messages = chatEntity.getMessages();
        if (messages != null && !messages.isEmpty()) {
            messageCount = messages.size();
            MessageEntity lastMessage = messages.get(messages.size() - 1);

            if (lastMessage != null) {
                text = lastMessage.getText();

                LocalDate createdAt = lastMessage.getCreatedAt();
                if (createdAt != null) {
                    timer = createdAt.toString();
                }
            }
        }

        return new ResponseGenericChatDto(
                id,
                commonId,
                commonName,
                text,
                timer,
                messageCount
        );
    }

    private ResponseGenericChatDto createDefaultResponse() {
        return new ResponseGenericChatDto(
                null,
                null,
                null,
                null,
                null,
                0
        );
    }

    public ResponseChatDto post(RequestChatDto request) {
        ChatEntity chat = new ChatEntity();

        chat.setUserAdminer(request.userAdminer());
        chat.setUserCommon(request.userCommon());
        repository.save(chat);

        return new ResponseChatDto(chat);
    }

    public ChatEntity findById(UUID id) {
        return this.repository.findById(id).orElseThrow(
                () -> new ChatException("Chat não encontrado")
        );
    }

    public ChatEntity findByUserId(UUID userId) {
        return this.repository.findByUserId(userId).orElseThrow(
                () -> new ChatException("Esse usuário não possuí um chat")
        );
    }

    public void setNewMessage(UUID id, MessageEntity message) {
        ChatEntity chat = this.findById(id);
        List<MessageEntity> list = chat.getMessages();
        list.add(message);
        this.repository.save(chat);
    }


}
