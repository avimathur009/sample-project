package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.AppOrder;
import com.example.demo.service.orderManagement.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<AppOrder> placeOrder(@RequestBody OrderDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppOrder> viewOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.viewOrder(id));
    }

    @GetMapping
    public ResponseEntity<List<AppOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppOrder>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}
