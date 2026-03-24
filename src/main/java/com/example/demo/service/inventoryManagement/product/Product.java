package com.example.demo.service.inventoryManagement.product;

import com.example.demo.service.inventoryManagement.enumClasses.Brand;
import com.example.demo.service.inventoryManagement.enumClasses.Category;

public interface Product {
    void viewProductDetails();

    Category getCategory();

    Brand getBrand();
}
