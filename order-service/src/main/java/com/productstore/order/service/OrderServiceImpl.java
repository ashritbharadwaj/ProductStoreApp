package com.productstore.order.service;

import com.productstore.order.dto.InventoryDto;
import com.productstore.order.dto.OrderDto;
import com.productstore.order.dto.ProductDto;
import com.productstore.order.entity.Order;
import com.productstore.order.exceptions.OrderCannotCreatedException;
import com.productstore.order.exceptions.OrderNotFoundException;
import com.productstore.order.repo.OrderRepository;
import com.productstore.order.serviceproxy.InventoryServiceProxy;
import com.productstore.order.serviceproxy.ProductServiceProxy;
import com.productstore.order.utils.OrderConversion;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryServiceProxy inventoryServiceProxy;
    private final ProductServiceProxy productServiceProxy;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    @Override
    public OrderDto addOrder(OrderDto orderDto) {
        log.info("Adding order: {}", orderDto);
        if (!checkInventory(orderDto)) {
            log.error("Order cannot be created due to insufficient inventory for product ID: {}", orderDto.getProductId());
            throw new OrderCannotCreatedException("Order cannot be created due to insufficient inventory");
        }
        Order order = OrderConversion.OrderDtoToOrder(orderDto);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("Created");
        orderRepository.save(order);
        updateInventory(orderDto);
        OrderDto returningOrderDto = OrderConversion.OrderToOrderDto(order);
        returningOrderDto.setProductDto(getProduct(returningOrderDto.getProductId()));
        kafkaTemplate.send("order-topic", 0, null, returningOrderDto);
        log.info("Order added successfully: {}", returningOrderDto);
        return returningOrderDto;
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        log.info("Updating order with ID: {}", orderId);
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order not found with ID: {}", orderId);
            return new OrderNotFoundException("Order not found with id: " + orderId);
        });
        String status = existingOrder.getStatus();
        orderDto.setOrderId(orderId);
        Order order = OrderConversion.OrderDtoToOrder(orderDto);
        orderRepository.save(order);
        OrderDto returningOrderDto = OrderConversion.OrderToOrderDto(order);
        if (!Objects.equals(status, returningOrderDto.getStatus())) {
            kafkaTemplate.send("order-topic", 0, null, returningOrderDto);
        }
        log.info("Order updated successfully: {}", returningOrderDto);
        return returningOrderDto;
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderDto orderDto) {
        log.info("Updating order status for order ID: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order not found with ID: {}", orderId);
            return new OrderNotFoundException("Order not found with id: " + orderDto.getOrderId());
        });
        String status = order.getStatus();
        order.setStatus(orderDto.getStatus());
        orderRepository.save(order);
        OrderDto returningOrderDto = OrderConversion.OrderToOrderDto(order);
        if (!Objects.equals(status, returningOrderDto.getStatus())) {
            kafkaTemplate.send("order-topic", 0, null, returningOrderDto);
        }
        log.info("Order status updated successfully: {}", returningOrderDto);
        return returningOrderDto;
    }

    @Override
    public void deleteOrderById(Long orderId) {
        log.info("Deleting order with ID: {}", orderId);
        orderRepository.delete(orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order not found with ID: {}", orderId);
            return new OrderNotFoundException("Order not found with id: " + orderId);
        }));
        log.info("Order deleted successfully with ID: {}", orderId);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        log.info("Fetching order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order not found with ID: {}", orderId);
            return new OrderNotFoundException("Order not found with id: " + orderId);
        });
        OrderDto orderDto = OrderConversion.OrderToOrderDto(order);
        log.info("Order fetched successfully: {}", orderDto);
        return orderDto;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        if (!orders.isEmpty()) {
            log.info("Orders fetched successfully");
            return orders.stream().map(OrderConversion::OrderToOrderDto).toList();
        }
        log.info("No orders found");
        return List.of();
    }

    public boolean checkInventory(OrderDto orderDto) {
        log.info("Checking inventory for product ID: {}", orderDto.getProductId());
        InventoryDto inventoryDto = inventoryServiceProxy.getInventoryByProductId(orderDto.getProductId()).getBody();
        if (inventoryDto == null) {
            log.error("Product not found with ID: {}", orderDto.getProductId());
            throw new OrderNotFoundException("Product not found with id: " + orderDto.getProductId());
        }
        boolean isAvailable = inventoryDto.getQuantity() >= orderDto.getQuantity();
        log.info("Inventory check result for product ID {}: {}", orderDto.getProductId(), isAvailable);
        return isAvailable;
    }

    public void updateInventory(OrderDto orderDto) {
        log.info("Updating inventory for product ID: {}", orderDto.getProductId());
        InventoryDto inventoryDto = inventoryServiceProxy.getInventoryByProductId(orderDto.getProductId()).getBody();
        if (inventoryDto == null) {
            log.error("Product not found with ID: {}", orderDto.getProductId());
            throw new OrderNotFoundException("Product not found with id: " + orderDto.getProductId());
        }
        inventoryDto.setQuantity(inventoryDto.getQuantity() - orderDto.getQuantity());
        inventoryServiceProxy.updateInventory(inventoryDto.getId(), inventoryDto);
        log.info("Inventory updated successfully for product ID: {}", orderDto.getProductId());
    }

    public ProductDto getProduct(String productId) {
        log.info("Fetching product with ID: {}", productId);
        ProductDto productDto = productServiceProxy.getProduct(productId).getBody();
        log.info("Product fetched successfully: {}", productDto);
        return productDto;
    }
}