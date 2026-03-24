package com.example.demo.service.inventoryManagement.productCategory;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.inventoryManagement.product.Product;

import java.util.List;

public class ClothingProductCategory implements ProductCategory {
    @Override
    public void addInsideProductCategory(Long inventoryId, ProductDto productDto) {

    }

    @Override
    public void removeFromProductCategory(Long inventoryId, ProductDto productDto) {

    }

    @Override
    public void viewProductsInThisCategory(Long inventoryId) {

    }

    @Override
    public List<Product> getListOfProductsInThisCategory(Long inventoryId) {
        return List.of();
    }
}
