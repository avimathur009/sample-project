package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private String inventoryName;
    private String address;
    private Integer maximumCapacity;
    private List<String> listOfCategories;
    private Long warehouseId;
}
