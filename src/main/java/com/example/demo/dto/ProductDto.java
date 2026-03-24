package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productName;
    private String productBrand;
    private String productCategory;
    private Integer productQuantity;
    private BigDecimal price;
    private LocalDate manufacturedDate;
    private Long inventoryId;
}
