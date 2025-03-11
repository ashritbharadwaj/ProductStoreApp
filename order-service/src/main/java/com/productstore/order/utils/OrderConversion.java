package com.productstore.order.utils;

import com.productstore.order.dto.OrderDto;
import com.productstore.order.entity.Order;

public class OrderConversion {

    public static Order OrderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderId(orderDto.getOrderId());
        order.setProductId(orderDto.getProductId());
        order.setQuantity(orderDto.getQuantity());
        order.setUserEmail(orderDto.getUserEmail());
        order.setStatus(orderDto.getStatus());
        order.setOrderDate(orderDto.getOrderDate());
        return order;
    }

    public static OrderDto OrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setProductId(order.getProductId());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setUserEmail(order.getUserEmail());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderDate(order.getOrderDate());
        return orderDto;
    }
}
