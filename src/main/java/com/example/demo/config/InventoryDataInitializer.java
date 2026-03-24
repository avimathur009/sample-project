package com.example.demo.config;

import com.example.demo.dto.InventoryDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.WarehouseDto;
import com.example.demo.service.InventoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.WarehouseService;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.Warehouse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class InventoryDataInitializer implements CommandLineRunner {

    private final WarehouseService warehouseService;
    private final InventoryService inventoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) {
        if (warehouseService.getAllWarehouses().isEmpty()) {
            log.info("Seeding inventory management data...");

            // --- Warehouses ---
            Warehouse wh1 = warehouseService.createWarehouse(
                    new WarehouseDto("North Warehouse", "123 Industrial Ave, Chicago, IL 60601", 5000));
            Warehouse wh2 = warehouseService.createWarehouse(
                    new WarehouseDto("South Warehouse", "456 Commerce Blvd, Atlanta, GA 30301", 3000));

            // --- Inventories ---
            Inventory inv1 = inventoryService.createInventory(new InventoryDto(
                    "Electronics Inventory", "Aisle A, North Warehouse", 1000,
                    List.of("ELECTRONICS"), wh1.getWarehouseId()));

            Inventory inv2 = inventoryService.createInventory(new InventoryDto(
                    "Clothing Inventory", "Aisle B, North Warehouse", 1500,
                    List.of("CLOTHING"), wh1.getWarehouseId()));

            Inventory inv3 = inventoryService.createInventory(new InventoryDto(
                    "Food Inventory", "Aisle A, South Warehouse", 800,
                    List.of("FOOD_AND_BEVERAGES"), wh2.getWarehouseId()));

            Inventory inv4 = inventoryService.createInventory(new InventoryDto(
                    "Mixed Inventory", "Aisle B, South Warehouse", 700,
                    List.of("ELECTRONICS", "HEALTH_AND_WELLNESS"), wh2.getWarehouseId()));

            // --- Products ---
            productService.createProduct(new ProductDto(
                    "iPhone 15 Pro", "Apple", "ELECTRONICS", 50,
                    new BigDecimal("999.99"), LocalDate.of(2023, 9, 15), inv1.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Samsung Galaxy S24", "Samsung", "ELECTRONICS", 40,
                    new BigDecimal("849.99"), LocalDate.of(2024, 1, 20), inv1.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Sony WH-1000XM5 Headphones", "Sony", "ELECTRONICS", 30,
                    new BigDecimal("349.99"), LocalDate.of(2023, 5, 10), inv1.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "MacBook Pro 16-inch", "Apple", "ELECTRONICS", 20,
                    new BigDecimal("2499.99"), LocalDate.of(2023, 11, 5), inv1.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Levi's 501 Original Jeans", "Levi's", "CLOTHING", 100,
                    new BigDecimal("59.99"), LocalDate.of(2023, 8, 1), inv2.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Nike Air Max 270", "Nike", "CLOTHING", 75,
                    new BigDecimal("129.99"), LocalDate.of(2023, 7, 15), inv2.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Adidas Ultraboost 22", "Adidas", "CLOTHING", 60,
                    new BigDecimal("149.99"), LocalDate.of(2023, 6, 20), inv2.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Organic Green Tea", "Twinings", "FOOD_AND_BEVERAGES", 200,
                    new BigDecimal("8.99"), LocalDate.of(2024, 1, 1), inv3.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Whey Protein Powder", "Optimum Nutrition", "FOOD_AND_BEVERAGES", 150,
                    new BigDecimal("49.99"), LocalDate.of(2024, 2, 1), inv3.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "Vitamin C 1000mg", "NOW Foods", "HEALTH_AND_WELLNESS", 300,
                    new BigDecimal("12.99"), LocalDate.of(2023, 12, 15), inv4.getInventoryId()));

            productService.createProduct(new ProductDto(
                    "JBL Wireless Earbuds", "JBL", "ELECTRONICS", 80,
                    new BigDecimal("79.99"), LocalDate.of(2023, 10, 10), inv4.getInventoryId()));

            log.info("Inventory data seeding complete.");
        }
    }
}
