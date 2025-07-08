package com.irrah.back_end.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
