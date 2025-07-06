package com.irrah.back_end.dtos.message;

import com.irrah.back_end.entities.MessageEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResponseMessageDto(
        UUID id,
        String status,
        LocalDate timestamp,
        String estimatedDelivery,
        BigDecimal cost,
        BigDecimal currentBalance
) {
   public ResponseMessageDto(MessageEntity message, BigDecimal currentBalance) {
       this(
               message.getId(),
               message.getStatus(),
               message.getCreatedAt(),
               "don't knmow",
               message.getPrice(),
               currentBalance
       );
   }

    public ResponseMessageDto(MessageEntity message) {
        this(
                message.getId(),
                message.getStatus(),
                message.getCreatedAt(),
                "don't knmow",
                message.getPrice(),
                null
        );
    }
}
