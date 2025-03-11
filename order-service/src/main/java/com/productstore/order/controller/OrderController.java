package com.productstore.order.controller;

import com.productstore.order.dto.OrderDto;
import com.productstore.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<OrderDto> addOrder(@RequestBody @Valid OrderDto orderDto) {
        OrderDto order = orderService.addOrder(orderDto);
        return ResponseEntity.created(null).body(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody @Valid OrderDto orderDto) {
        OrderDto order = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.accepted().body(order);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.updateOrderStatus(orderId, orderDto);
        return ResponseEntity.accepted().body(order);
    }

    // Other endpoints
}
