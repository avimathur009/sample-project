package com.example.demo.service.orderManagement.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CashPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(BigDecimal amount) {
        // Cash is always accepted at point of delivery — no external gateway needed
        return true;
    }

    @Override
    public String getPaymentType() {
        return "CASH";
    }
}
