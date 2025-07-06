package com.irrah.back_end.services;

import com.irrah.back_end.dtos.chat.RequestChatDto;
import com.irrah.back_end.dtos.chat.ResponseChatDto;
import com.irrah.back_end.dtos.chat.ResponseGenericChatDto;
import com.irrah.back_end.entities.ChatEntity;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository repository;

    @Autowired
    private MessageService messageService;

    public List<ResponseGenericChatDto> getChats() {
        List<ChatEntity> chats = repository.findAll();
        List<ResponseGenericChatDto> dto = new ArrayList<ResponseGenericChatDto>();
        for(ChatEntity chat : chats) {
            MessageEntity message = messageService.getLastMessageFromUser(chat.getUserCommon().getId());
        }
        return dto;
    }

    public ResponseChatDto post(RequestChatDto request) {
        ChatEntity chat = new ChatEntity();

        chat.setUserAdminer(request.userAdminer());
        chat.setUserCommon(request.userCommon());
        repository.save(chat);

        return new ResponseChatDto(chat);
    }

}
