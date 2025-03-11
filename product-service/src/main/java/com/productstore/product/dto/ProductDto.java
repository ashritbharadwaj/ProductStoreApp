package com.productstore.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productId;

//    @NotEmpty(message = "codeNumber is required")
//    private String codeNumber;

    private String name;

    @NotNull(message = "price is required")
    @Range(min = 0, message = "price must be greater than 0")
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
