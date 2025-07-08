package com.irrah.back_end.dtos.message;

import com.irrah.back_end.enums.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestMessageDto(
        @NotNull(message = "Selecione qual chat de mensagem deseje enviar a mensagem")
        UUID conversationId,
        @NotBlank(message = "Envie alguma mensagem")
        String content,
        @NotNull(message = "Selecione o tipo de mensagem que deseja")
        String priority
) { }