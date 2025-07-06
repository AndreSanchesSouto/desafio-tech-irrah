package com.irrah.back_end.services;
import com.irrah.back_end.dtos.chat.RequestChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
}
