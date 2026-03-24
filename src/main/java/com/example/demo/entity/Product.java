package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false)
    String productName;

    @Column(name = "product_brand", nullable = false)
    String productBrand;

    @Column(name = "product_category", nullable = false)
    String productCategory;

    @Column(name = "quantity")
    Integer productQuantity;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "manufactured_date")
    LocalDate manufacturedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    @JsonIgnore
    Inventory inventory;

    @Column(name = "created_at", nullable = false)
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
