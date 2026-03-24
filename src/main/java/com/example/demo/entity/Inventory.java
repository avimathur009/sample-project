package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "inventory_name", nullable = false)
    String inventoryName;

    @Column(name = "address")
    String address;

    @Column(name = "current_capacity")
    Integer currentCapacity = 0;

    @Column(name = "maximum_capacity", nullable = false)
    Integer maximumCapacity;

    @ElementCollection
    @CollectionTable(name = "inventory_categories", joinColumns = @JoinColumn(name = "inventory_id"))
    @Column(name = "category")
    List<String> listOfCategories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference
    Warehouse warehouse;

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
