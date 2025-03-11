package com.productstore.notify.service;


import com.productstore.order.dto.OrderDto;

public interface notifyService {
    public void listenOrder(OrderDto orderDto);
}