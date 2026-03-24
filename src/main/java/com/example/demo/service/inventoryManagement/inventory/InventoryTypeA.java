package com.example.demo.service.inventoryManagement.inventory;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.inventoryManagement.enumClasses.Category;
import com.example.demo.service.inventoryManagement.product.Product;

public class InventoryTypeA implements Inventory {
    @Override
    public void addProducts(Long inventoryId, ProductDto product) {
        //algo to select the correct product category
        //then set inside that product category
    }

    @Override
    public void removeProducts(Long inventoryId, ProductDto product) {

    }

    @Override
    public void viewProductsInInventory(Long InventoryId) {
        //algo to select the correct product category
        //then show those products
    }
}
