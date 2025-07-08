package com.irrah.back_end.websocket;


import com.irrah.back_end.dtos.message.ResponseMessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Controller
public class MessageWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageStatusUpdate(ResponseMessageDto messageDto) {
        System.out.println("Enviando atualização de mensagem via WebSocket: " + messageDto);
        messagingTemplate.convertAndSend("/topic/messages", messageDto);
    }
}

