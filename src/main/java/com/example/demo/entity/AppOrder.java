package com.example.demo.entity;

import com.example.demo.util.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

// Named AppOrder; table is 'orders' because ORDER is a SQL reserved word
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
public class AppOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    // Human-readable order reference generated on persist
    @Column(name = "order_ref", unique = true, nullable = false)
    private String id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",  column = @Column(name = "del_street")),
        @AttributeOverride(name = "city",    column = @Column(name = "del_city")),
        @AttributeOverride(name = "state",   column = @Column(name = "del_state")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "del_zip_code")),
        @AttributeOverride(name = "country", column = @Column(name = "del_country"))
    })
    private Address delAdd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse wareha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private AppUser user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        id = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Business method — delegates to OrderService in service layer
    public void viewOrder() {
        System.out.println("Order[" + id + "] | User: " + (user != null ? user.getName() : "N/A")
                + " | Status: " + (payment != null ? payment.getStatus() : "N/A"));
    }
}
