package com.productstore.order.serviceproxy;

import com.productstore.order.dto.InventoryDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name="inventory", url="http://localhost:8085/api/inventories")
@FeignClient(name="inventory", url="${inventory-service.url}")
public interface InventoryServiceProxy {
    @GetMapping("api/inventories/productid={productId}")
    public ResponseEntity<InventoryDto> getInventoryByProductId(@PathVariable String productId);

    @PutMapping("api/inventories/{inventoryId}")
    public ResponseEntity<InventoryDto> updateInventory(@PathVariable Long inventoryId, @RequestBody @Valid InventoryDto inventoryDto);
}
