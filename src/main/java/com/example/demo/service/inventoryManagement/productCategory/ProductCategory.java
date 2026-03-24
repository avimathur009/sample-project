package com.example.demo.service.inventoryManagement.productCategory;

import com.example.demo.dto.ProductDto;
import com.example.demo.service.inventoryManagement.product.Product;

import java.util.List;

public interface ProductCategory {
    void addInsideProductCategory(Long inventoryId, ProductDto productDto);

    void removeFromProductCategory(Long inventoryId, ProductDto productDto);

    void viewProductsInThisCategory(Long inventoryId);

    List<Product> getListOfProductsInThisCategory(Long inventoryId);
}
