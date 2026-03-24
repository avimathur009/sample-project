package com.example.demo.service.orderManagement.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean processPayment(BigDecimal amount) {
        // Placeholder: integrate with a real payment gateway (Stripe, Razorpay, etc.)
        return true;
    }

    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }
}
