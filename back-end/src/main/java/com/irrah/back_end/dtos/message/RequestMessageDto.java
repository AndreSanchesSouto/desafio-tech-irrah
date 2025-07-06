package com.irrah.back_end.dtos.message;

import java.util.UUID;

public record RequestMessageDto(
        UUID conversationId,     // ID da conversa existente
        UUID recipientId,       // Para novas conversas
        String content,
        String priority,
        String userPlanType
) { }