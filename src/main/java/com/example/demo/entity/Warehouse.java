package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "warehouse_name", nullable = false)
    String warehouseName;

    @Column(name = "address")
    String address;

    @Column(name = "current_capacity")
    Integer currentCapacity = 0;

    @Column(name = "maximum_capacity", nullable = false)
    Integer maximumCapacity;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Inventory> inventories = new ArrayList<>();

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
