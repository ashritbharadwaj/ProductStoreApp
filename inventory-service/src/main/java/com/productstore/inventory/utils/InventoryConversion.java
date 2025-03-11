package com.productstore.inventory.utils;

import com.productstore.inventory.dto.InventoryDto;
import com.productstore.inventory.entity.Inventory;

public class InventoryConversion {
    public static InventoryDto inventoryToInventoryDto(Inventory inventory) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setId(inventory.getId());
        inventoryDto.setProductId(inventory.getProductId());
        inventoryDto.setQuantity(inventory.getQuantity());
        return inventoryDto;
    }

    public static Inventory inventoryDtoToInventory(InventoryDto inventoryDto) {
        Inventory inventory = new Inventory();
        inventory.setId(inventoryDto.getId());
        inventory.setProductId(inventoryDto.getProductId());
        inventory.setQuantity(inventoryDto.getQuantity());
        return inventory;
    }
}
