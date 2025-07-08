package com.irrah.back_end.services;

import com.irrah.back_end.dtos.payment.ResponsePayment;
import com.irrah.back_end.entities.MessageEntity;
import com.irrah.back_end.entities.PaymentEntity;
import com.irrah.back_end.entities.UserEntity;
import com.irrah.back_end.enums.MessageStatus;
import com.irrah.back_end.enums.PlanType;
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
        PaymentEntity payment = new PaymentEntity();
        this.discountFromUser(user, message);
        payment.setClient(message.getUserSender());
        payment.setPrice(message.getPrice());
        payment.setType(message.getType());
        repository.save(payment);
    }

    private void discountFromUser(UserEntity user, MessageEntity message) {
        if(user.getPlanType().equals(PlanType.POSTPAID.getPlanType())) this.handlePostpaidMessage(user, message);
        if(user.getPlanType().equals(PlanType.PREPAID.getPlanType())) this.handlePrepaidMessage(user, message);
    }

    public void handlePostpaidMessage(UserEntity user, MessageEntity message) {
        if (this.userHasMonthLimit(user, message)) {
            message.setStatus(MessageStatus.DELIVERED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }

    public void handlePrepaidMessage(UserEntity user, MessageEntity message) {
        if (this.userHasMoney(user, message)) {
            message.setStatus(MessageStatus.DELIVERED.getStatus());
        } else {
            message.setStatus(MessageStatus.FAILED.getStatus());
        }
    }

    public boolean userHasMonthLimit(UserEntity user, MessageEntity message) {
        boolean canPay = user.getMonthLimit().compareTo(message.getPrice()) >= 0;
        if(canPay) {
            BigDecimal actualValue = user.getMonthLimit();
            user.setMonthLimit(actualValue.subtract(message.getPrice()).toString());
        }
        return canPay;
    }

    public boolean userHasMoney(UserEntity user, MessageEntity message) {
        boolean canPay = user.getBalance().compareTo(message.getPrice()) >= 0;
        if(canPay) {
            BigDecimal actualValue = user.getBalance();
            user.setBalance(actualValue.subtract(message.getPrice()).toString());
        }
        return canPay;
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
