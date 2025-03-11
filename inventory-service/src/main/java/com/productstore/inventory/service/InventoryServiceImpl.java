package com.productstore.inventory.service;

import com.productstore.inventory.dto.InventoryDto;
import com.productstore.inventory.entity.Inventory;
import com.productstore.inventory.exceptions.InventoryNotFoundException;
import com.productstore.inventory.repo.InventoryRepository;
import com.productstore.inventory.utils.InventoryConversion;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryDto addInventory(InventoryDto inventoryDto) {
        log.info("Adding inventory: {}", inventoryDto);
        Inventory existingInventory = inventoryRepository.findByProductId(inventoryDto.getProductId());
        if (existingInventory != null) {
            existingInventory.setQuantity(existingInventory.getQuantity() + inventoryDto.getQuantity());
            inventoryRepository.save(existingInventory);
            log.info("Inventory updated successfully: {}", existingInventory);
            return InventoryConversion.inventoryToInventoryDto(existingInventory);
        }
        Inventory inventory = InventoryConversion.inventoryDtoToInventory(inventoryDto);
        inventoryRepository.save(inventory);
        log.info("Inventory added successfully: {}", inventory);
        return InventoryConversion.inventoryToInventoryDto(inventory);
    }

    @Override
    public InventoryDto updateInventory(Long inventoryId, InventoryDto inventoryDto) {
        log.info("Updating inventory with ID: {}", inventoryId);
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> {
            log.error("Inventory not found with ID: {}", inventoryId);
            return new InventoryNotFoundException("Inventory not found with id: " + inventoryId);
        });

        inventory.setProductId(inventoryDto.getProductId());
        inventory.setQuantity(inventoryDto.getQuantity());
        inventoryRepository.save(inventory);
        log.info("Inventory updated successfully: {}", inventory);
        return InventoryConversion.inventoryToInventoryDto(inventory);
    }

    @Override
    public void deleteInventoryById(Long inventoryId) {
        log.info("Deleting inventory with ID: {}", inventoryId);
        inventoryRepository.delete(inventoryRepository.findById(inventoryId).orElseThrow(() -> {
            log.error("Inventory not found with ID: {}", inventoryId);
            return new InventoryNotFoundException("Inventory not found with id: " + inventoryId);
        }));
        log.info("Inventory deleted successfully with ID: {}", inventoryId);
    }

    @Override
    public InventoryDto getInventoryById(Long inventoryId) {
        log.info("Fetching inventory with ID: {}", inventoryId);
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> {
            log.error("Inventory not found with ID: {}", inventoryId);
            return new InventoryNotFoundException("Inventory not found with id: " + inventoryId);
        });
        InventoryDto inventoryDto = InventoryConversion.inventoryToInventoryDto(inventory);
        log.info("Inventory fetched successfully: {}", inventoryDto);
        return inventoryDto;
    }

    @Override
    public List<InventoryDto> getAllInventories() {
        log.info("Fetching all inventories");
        List<Inventory> inventories = inventoryRepository.findAll();
        if (!inventories.isEmpty()) {
            log.info("Inventories fetched successfully");
            return inventories.stream().map(InventoryConversion::inventoryToInventoryDto).toList();
        }
        log.info("No inventories found");
        return List.of();
    }

    @Override
    public InventoryDto getInventoryByProductId(String productId) {
        log.info("Fetching inventory for product ID: {}", productId);
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            log.error("Inventory not found with product ID: {}", productId);
            throw new InventoryNotFoundException("Inventory not found with product id: " + productId);
        }
        InventoryDto inventoryDto = InventoryConversion.inventoryToInventoryDto(inventory);
        log.info("Inventory fetched successfully for product ID: {}", productId);
        return inventoryDto;
    }
}