package com.productstore.inventory.controller;

import com.productstore.inventory.dto.InventoryDto;
import com.productstore.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long inventoryId) {
        InventoryDto inventoryDto = inventoryService.getInventoryById(inventoryId);
        return ResponseEntity.ok(inventoryDto);
    }

    @GetMapping("")
    public ResponseEntity<List<InventoryDto>> getAllInventories() {
        List<InventoryDto> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventoryById(@PathVariable Long inventoryId) {
        inventoryService.deleteInventoryById(inventoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<InventoryDto> addInventory(@RequestBody @Valid InventoryDto inventoryDto) {
        InventoryDto addedInventory = inventoryService.addInventory(inventoryDto);
        return ResponseEntity.created(null).body(addedInventory);
    }

    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> updateInventory(@PathVariable Long inventoryId, @RequestBody @Valid InventoryDto inventoryDto) {
        InventoryDto updatedInventory = inventoryService.updateInventory(inventoryId, inventoryDto);
        return ResponseEntity.ok(updatedInventory);
    }

    @GetMapping("/productid={productId}")
    public ResponseEntity<InventoryDto> getInventoryByProductId(@PathVariable String productId) {
        InventoryDto inventoryDto = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventoryDto);
    }

    //other endpoints
}
