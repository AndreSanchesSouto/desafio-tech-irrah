package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.message.RequestMessageDto;
import com.irrah.back_end.dtos.message.RequestMessagesToCheckDto;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @PostMapping
    public ResponseEntity<ResponseMessageDto> post(@RequestBody @Valid RequestMessageDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.post(request));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<ResponseMessageDto>> getMessages(@PathVariable UUID chatId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getMessages(chatId));
    }

    @PostMapping("/read")
    public ResponseEntity<List<ResponseMessageDto>> readMessages(@RequestBody RequestMessagesToCheckDto messages) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.markAsRead(messages));
    }
}
