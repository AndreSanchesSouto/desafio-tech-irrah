package com.irrah.back_end.services;
import com.irrah.back_end.dtos.chat.RequestChatDto;
import com.irrah.back_end.entities.ChatEntity;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrchestratorService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    public void postChat(RequestChatDto request) {
        this.chatService.post(request);
    }

    public UserEntity findCurrentUser() {
        return this.userService.findCurrentUser();
    }

    public boolean userCanPayMessageCost(UserEntity user, MessageEntity message) {
        return userService.canPayMessageCost(user, message);
    }

    public boolean userHasMonthLimit(UserEntity user, MessageEntity message) {
        return userService.hasMonthLimit(user, message);
    }

    public UserEntity userFindById(UUID id) {
        return userService.findById(id);
    }

    public ChatEntity chatGetById(UUID id) {
        return chatService.findById(id);
    }

    public void chatSetNewMassage(UUID id, MessageEntity message) {
        this.chatService.setNewMessage(id, message);
    }
}
