package com.example.demo.service.orderManagement.order;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.orderManagement.cart.CartService;
import com.example.demo.service.orderManagement.invoice.InvoiceService;
import com.example.demo.service.orderManagement.payment.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final InvoiceService invoiceService;

    // Spring injects ALL PaymentStrategy beans — allows open/closed extensibility
    private final List<PaymentStrategy> paymentStrategies;

    /**
     * Full order placement flow:
     * 1. Validate user + warehouse
     * 2. Calculate total from cart
     * 3. Check & deduct inventory stock for each product
     * 4. Process payment via selected strategy
     * 5. Generate invoice
     * 6. Persist order and clear cart
     */
    public AppOrder placeOrder(OrderDto dto) {
        AppUser user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));

        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found: " + dto.getWarehouseId()));

        Cart cart = user.getCart();
        if (cart == null || cart.getProductVsCount().isEmpty()) {
            throw new RuntimeException("Cart is empty — add products before placing an order.");
        }

        // 1. Calculate order total & deduct inventory stock
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : cart.getProductVsCount().entrySet()) {
            Product product = entry.getKey();
            int requestedQty = entry.getValue();

            if (product.getProductQuantity() < requestedQty) {
                throw new RuntimeException("Insufficient stock for: " + product.getProductName()
                        + " (available: " + product.getProductQuantity() + ", requested: " + requestedQty + ")");
            }

            // Deduct stock from inventory
            product.setProductQuantity(product.getProductQuantity() - requestedQty);
            productRepository.save(product);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(requestedQty)));
        }

        // 2. Process payment using selected strategy
        PaymentStrategy strategy = paymentStrategies.stream()
                .filter(s -> s.getPaymentType().equalsIgnoreCase(dto.getPaymentType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unsupported payment type: " + dto.getPaymentType()
                        + ". Valid types: CASH, CREDIT_CARD"));

        boolean success = strategy.processPayment(total);

        Payment payment = new Payment();
        payment.setPaymentType(strategy.getPaymentType());
        payment.setAmount(total);
        payment.setStatus(success ? "COMPLETED" : "FAILED");
        paymentRepository.save(payment);

        if (!success) {
            throw new RuntimeException("Payment failed — order not placed.");
        }

        // 3. Generate invoice
        Invoice invoice = invoiceService.generateInvoice(total);

        // 4. Build and persist order
        AppOrder order = new AppOrder();
        order.setDelAdd(dto.getDeliveryAddress());
        order.setWareha(warehouse);
        order.setUser(user);
        order.setPayment(payment);
        order.setInvoice(invoice);

        AppOrder savedOrder = orderRepository.save(order);

        // 5. Clear cart after successful order
        cartService.emptyCart(cart.getCartId());

        return savedOrder;
    }

    public AppOrder viewOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }

    public List<AppOrder> getOrdersByUser(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public List<AppOrder> getAllOrders() {
        return orderRepository.findAll();
    }
}
