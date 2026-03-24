package com.example.demo.service.inventoryManagement.inventory;

import com.example.demo.dto.ProductDto;

public interface Inventory {
    void addProducts(Long inventoryId, ProductDto product);

    void removeProducts(Long inventoryId, ProductDto product);

    void viewProductsInInventory(Long InventoryId);
}
