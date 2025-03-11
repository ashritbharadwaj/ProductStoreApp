package com.productstore.order.service;

import com.productstore.order.dto.OrderDto;

import java.util.List;

public interface OrderService {

    public OrderDto addOrder(OrderDto orderDto);

    public OrderDto updateOrder(Long orderId,OrderDto orderDto);

    public OrderDto updateOrderStatus(Long orderId,OrderDto orderDto);

    public void deleteOrderById(Long orderId);

    public OrderDto getOrderById(Long orderId);

    public List<OrderDto> getAllOrders();
}
