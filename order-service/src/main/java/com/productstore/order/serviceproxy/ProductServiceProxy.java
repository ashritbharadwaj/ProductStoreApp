package com.productstore.order.serviceproxy;

import com.productstore.order.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "product-service", url = "http://localhost:8084/api/products")
@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductServiceProxy {
    @GetMapping("api/products/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId);
}
