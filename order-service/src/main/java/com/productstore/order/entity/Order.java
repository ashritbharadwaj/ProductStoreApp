package com.productstore.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long orderId;

    private String ProductId;
    private Integer quantity;
    private String userEmail;
    private String status;
    private LocalDateTime orderDate;

    public Order(String productId, Integer quantity, String status, LocalDateTime orderDate, String userEmail) {
        ProductId = productId;
        this.quantity = quantity;
        this.userEmail = userEmail;
        this.status = status;
        this.orderDate = orderDate;
    }
}
