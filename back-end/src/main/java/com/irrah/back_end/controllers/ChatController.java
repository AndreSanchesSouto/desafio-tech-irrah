package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.chat.ResponseGenericChatDto;
import com.irrah.back_end.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private ChatService service;

    @GetMapping
    public ResponseEntity<List<ResponseGenericChatDto>> getChats() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getChats());
    }

}
