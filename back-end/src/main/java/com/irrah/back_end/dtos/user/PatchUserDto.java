package com.irrah.back_end.dtos.user;

import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.UserStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PatchUserDto(
        String name,
        String document,
        String balance,
        String number,
        String planType,
        String status
) { }

