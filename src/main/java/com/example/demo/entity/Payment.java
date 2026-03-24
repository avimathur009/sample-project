package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    // CASH or CREDIT_CARD — set by the chosen PaymentStrategy
    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    // PENDING, COMPLETED, FAILED
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @PrePersist
    void onCreate() {
        paidAt = LocalDateTime.now();
    }
}
