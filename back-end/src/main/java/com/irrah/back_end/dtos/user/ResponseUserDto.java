package com.irrah.back_end.dtos.user;

import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.UserStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ResponseUserDto(
        UUID id,
        String name,
        String documentId,
        String documentType,
        BigDecimal balance,
        BigDecimal number,
        String planType,
        boolean active
) {
    public ResponseUserDto(UserEntity user) {
        this(
                user.getId(),
                user.getName(),
                user.getDocument(),
                detectDocumentType(user.getDocument()),
                user.getBalance(),
                user.getMonthLimit(),
                user.getPlanType(),
                user.getStatus().equalsIgnoreCase(UserStatus.ACTIVE.getUserStatus())
        );
    }

    private static String detectDocumentType(String document) {
        document = document.replaceAll("\\D", "");
        if (document.length() == 11) return "CPF";
        if (document.length() == 14) return "CNPJ";
        return "DESCONHECIDO";
    }
}

