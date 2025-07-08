package com.irrah.back_end.dtos.message;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RequestMessagesToCheckDto(
    List<UUID> messagesId
) { }
