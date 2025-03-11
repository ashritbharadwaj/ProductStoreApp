package com.productstore.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long orderId;

    @NotEmpty(message = "ProductId is required")
    private String productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotEmpty(message = "UserEmail is required")
    private String userEmail;

    private String status;

    private LocalDateTime orderDate;

    private ProductDto productDto;

    public OrderDto(String productId, Integer quantity, String status, LocalDateTime orderDate) {
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
    }

    public OrderDto(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
