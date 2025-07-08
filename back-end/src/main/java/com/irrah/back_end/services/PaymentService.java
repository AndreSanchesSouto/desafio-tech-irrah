package com.irrah.back_end.services;

import com.irrah.back_end.dtos.payment.ResponsePayment;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.PaymentEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public void post(UserEntity user, MessageEntity message) {
        BigDecimal actualValue = user.getBalance();
        user.setBalance(actualValue.subtract(message.getPrice()).toString());
        PaymentEntity payment = new PaymentEntity();
        payment.setClient(message.getUserSender());
        payment.setPrice(message.getPrice());
        payment.setType(message.getType());
        repository.save(payment);
    }

    public List<PaymentEntity> findByUserId(UUID userId) {
        return this.repository.findByUserId(userId);
    }

    public List<ResponsePayment> getByUserId(UUID userId) {
        List<PaymentEntity> payments = this.findByUserId(userId);
        return payments
                .stream()
                .map(ResponsePayment::new)
                .toList();
    }
}
