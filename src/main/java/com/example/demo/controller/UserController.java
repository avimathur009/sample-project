package com.example.demo.controller;

import com.example.demo.dto.CartItemDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.AppOrder;
import com.example.demo.entity.AppUser;
import com.example.demo.service.orderManagement.cart.CartService;
import com.example.demo.service.orderManagement.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartService cartService;

    // ── User CRUD ──────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<AppUser> addUser(@RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ── Profile ────────────────────────────────────────────────────────────────

    @GetMapping("/{id}/profile")
    public ResponseEntity<AppUser> checkProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.checkProfile(id));
    }

    // ── Orders ─────────────────────────────────────────────────────────────────

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<AppOrder>> seeOrders(@PathVariable Long id) {
        return ResponseEntity.ok(userService.seeOrders(id));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<AppOrder> placeOrder(@PathVariable Long id, @RequestBody OrderDto dto) {
        dto.setUserId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.placeOrder(dto));
    }

    // ── Cart ───────────────────────────────────────────────────────────────────

    @PostMapping("/{id}/cart")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestBody CartItemDto dto) {
        Long cartId = userService.getUserById(id).getCart().getCartId();
        return ResponseEntity.ok(cartService.addToCart(cartId, dto.getProductId(), dto.getQuantity()));
    }

    @DeleteMapping("/{id}/cart/{productId}")
    public ResponseEntity<?> deleteFromCart(@PathVariable Long id, @PathVariable Long productId) {
        Long cartId = userService.getUserById(id).getCart().getCartId();
        return ResponseEntity.ok(cartService.deleteFromCart(cartId, productId));
    }

    @DeleteMapping("/{id}/cart")
    public ResponseEntity<?> emptyCart(@PathVariable Long id) {
        Long cartId = userService.getUserById(id).getCart().getCartId();
        return ResponseEntity.ok(cartService.emptyCart(cartId));
    }
}
