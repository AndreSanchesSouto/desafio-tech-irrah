package com.irrah.back_end.controllers;

import com.irrah.back_end.dtos.payment.ResponsePayment;
import com.irrah.back_end.entities.PaymentEntity;
import com.irrah.back_end.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping("/{userId}")
    public ResponseEntity<List<ResponsePayment>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getByUserId(userId));
    }

}
