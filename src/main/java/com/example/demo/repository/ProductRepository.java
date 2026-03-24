package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductCategory(String category);

    List<Product> findByInventory_InventoryId(Long inventoryId);

    List<Product> findByProductBrand(String brand);

    List<Product> findByProductNameContainingIgnoreCase(String name);
}
