package com.example.demo.service.inventoryManagement.product;

import com.example.demo.service.inventoryManagement.enumClasses.Brand;
import com.example.demo.service.inventoryManagement.enumClasses.Category;

public class ElectronicProduct implements Product{
    @Override
    public void viewProductDetails() {
        //db touchpoint
    }

    @Override
    public Category getCategory() {
        //db touchpoint
        return null;
    }

    @Override
    public Brand getBrand() {
        //db touchpoint
        return null;
    }
}
