package com.example.demo.service.inventoryManagement;

import com.example.demo.service.inventoryManagement.enumClasses.Category;
import com.example.demo.service.inventoryManagement.productCategory.ClothingProductCategory;
import com.example.demo.service.inventoryManagement.productCategory.ElectronicProductCategory;
import com.example.demo.service.inventoryManagement.productCategory.FoodAndBeveragesProductCategory;
import com.example.demo.service.inventoryManagement.productCategory.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FactoryOfProductCategory {

    ProductCategory getProductCategory(Category category) {
        switch (category) {
            case ELECTRONICS -> { return new ElectronicProductCategory(); }

            case CLOTHING -> { return new ClothingProductCategory(); }

            case FOOD_AND_BEVERAGES -> { return new FoodAndBeveragesProductCategory(); }

            default -> {
                log.warn("No ProductCategory implementation found for category: {}", category);
                throw new IllegalArgumentException("Unsupported category: " + category);
            }
        }
    }
}
