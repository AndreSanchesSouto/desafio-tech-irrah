package com.irrah.back_end.dtos.message;

import com.irrah.back_end.enums.MessageStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ResponseGenericMessageDto(
        UUID id,
        MessageStatus status,
        BigDecimal price
) { }
