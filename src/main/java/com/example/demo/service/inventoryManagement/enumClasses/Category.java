package com.example.demo.service.inventoryManagement.enumClasses;

import lombok.Getter;

@Getter
public enum Category {

    ELECTRONICS(1),
    CLOTHING(2),
    FOOD_AND_BEVERAGES(3),
    HOME_AND_FURNITURE(4),
    BEAUTY_AND_PERSONAL_CARE(5),
    SPORTS_AND_OUTDOORS(6),
    TOYS_AND_GAMES(7),
    BOOKS_AND_STATIONERY(8),
    AUTOMOTIVE(9),
    HEALTH_AND_WELLNESS(10),
    JEWELRY_AND_ACCESSORIES(11),
    OFFICE_SUPPLIES(12);

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public String getString() {
        return this.toString();
    }
}
