package com.irrah.back_end.dtos.chat;

import com.irrah.back_end.entities.ChatEntity;

import java.util.UUID;

public record ResponseChatDto(
        UUID id,
        UUID recipientId
) {
    public ResponseChatDto(ChatEntity chat) {
        this(
                chat.getId(),
                chat.getUserCommon().getId()
        );
    }
}
