package com.example.demo.dto;

import com.example.demo.util.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long userId;
    private Long warehouseId;
    private Address deliveryAddress;
    // Must match a registered PaymentStrategy type: "CASH" or "CREDIT_CARD"
    private String paymentType;
}
