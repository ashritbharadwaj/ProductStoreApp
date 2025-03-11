package com.productstore.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productId;

//    @NotEmpty(message = "codeNumber is required")
//    private String codeNumber;

    private String name;

    private double price;

    private String category;

    private String description;

//    public ProductDto(String codeNumber, String name, double price, String category, String description) {
//        this.codeNumber = codeNumber;
//        this.name = name;
//        this.price = price;
//        this.category = category;
//        this.description = description;
//    }


    public ProductDto(String name, double price, String category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }
}
