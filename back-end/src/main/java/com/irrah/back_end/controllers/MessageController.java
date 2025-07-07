package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.message.RequestMessageDto;
import com.irrah.back_end.dtos.message.ResponseMessageDto;
import com.irrah.back_end.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @PostMapping
    public ResponseEntity<ResponseMessageDto> post(@RequestBody @Valid RequestMessageDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.post(request));
    }

}
