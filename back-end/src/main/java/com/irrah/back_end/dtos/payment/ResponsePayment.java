package com.irrah.back_end.dtos.payment;

import com.irrah.back_end.entities.PaymentEntity;
import com.irrah.back_end.entities.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResponsePayment(
        UUID id,
        String type,
        BigDecimal price,
        LocalDate createdAt
) {
    public ResponsePayment(PaymentEntity payment) {
        this(
                payment.getId(),
                payment.getType(),
                payment.getPrice(),
                payment.getCreatedAt()
        );
    }
}
