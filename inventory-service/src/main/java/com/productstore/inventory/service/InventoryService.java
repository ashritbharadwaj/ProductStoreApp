package com.productstore.inventory.service;

import com.productstore.inventory.dto.InventoryDto;

import java.util.List;

public interface InventoryService {
    public InventoryDto addInventory(InventoryDto inventoryDto);

    public InventoryDto updateInventory(Long inventoryId,InventoryDto inventoryDto);

    public void deleteInventoryById(Long inventoryId);

    public InventoryDto getInventoryById(Long inventoryId);

    public List<InventoryDto> getAllInventories();

    InventoryDto getInventoryByProductId(String productId);
}
