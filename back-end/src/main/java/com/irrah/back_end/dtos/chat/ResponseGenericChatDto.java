package com.irrah.back_end.dtos.chat;

import java.util.UUID;

public record ResponseGenericChatDto(
        UUID id,
        UUID recipientId,
        String recipientName,
        String lastMessageContent,
        String lastMessageTime,
        Integer number
) { }
