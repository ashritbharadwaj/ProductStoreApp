package com.productstore.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
//@Entity
//@Table(name = "product")
public class Product {
    @Id
//    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private String productId;

//    private String codeNumber;
    private String name;
    private double price;
    private String category;
    private String description;

//    public Product(String codeNumber, String name, double price, String category, String description) {
//        this.codeNumber = codeNumber;
//        this.name = name;
//        this.price = price;
//        this.category = category;
//        this.description = description;
//    }


    public Product(String name, double price, String category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }
}
