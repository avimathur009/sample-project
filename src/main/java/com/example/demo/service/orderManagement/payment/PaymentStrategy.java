package com.example.demo.service.orderManagement.payment;

import java.math.BigDecimal;

/**
 * Strategy interface for payment processing.
 * Add new payment methods (UPI, NetBanking, etc.) by implementing this interface
 * and registering the bean — no changes required elsewhere.
 */
public interface PaymentStrategy {

    /**
     * Attempt to process the given amount.
     *
     * @param amount the amount to charge
     * @return true if payment succeeded, false otherwise
     */
    boolean processPayment(BigDecimal amount);

    /**
     * Unique identifier used by callers to select the correct strategy.
     * Must match the paymentType field in OrderDto (e.g. "CASH", "CREDIT_CARD").
     */
    String getPaymentType();
}
