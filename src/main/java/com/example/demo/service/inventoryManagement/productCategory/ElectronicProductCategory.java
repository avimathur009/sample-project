package com.example.demo.service.inventoryManagement.productCategory;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.inventoryManagement.product.Product;

import java.util.List;

public class ElectronicProductCategory implements ProductCategory {

    @Override
    public void addInsideProductCategory(Long inventoryId, ProductDto productDto) {
        //db touchpoint
    }

    @Override
    public void removeFromProductCategory(Long inventoryId, ProductDto productDto) {
        //db touchpoint
    }

    @Override
    public void viewProductsInThisCategory(Long inventoryId) {
        //db touchpoint
    }

    @Override
    public List<Product> getListOfProductsInThisCategory(Long inventoryId) {
        //db touchpoint
        return List.of();
    }
}
