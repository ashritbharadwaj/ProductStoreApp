package com.productstore.inventory.service;

import com.productstore.inventory.dto.InventoryDto;
import com.productstore.inventory.entity.Inventory;
import com.productstore.inventory.exceptions.InventoryNotFoundException;
import com.productstore.inventory.repo.InventoryRepository;
import com.productstore.inventory.utils.InventoryConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;
    private InventoryDto inventoryDto;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(1L, "Product1", 100);
        inventoryDto = new InventoryDto(1L, "Product1", 100);
    }

    @Test
    void testAddInventory_NewProduct() {
        when(inventoryRepository.findByProductId(anyString())).thenReturn(null);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        InventoryDto result = inventoryService.addInventory(inventoryDto);
        assertNotNull(result);
        assertEquals(inventoryDto.getProductId(), result.getProductId());
        verify(inventoryRepository, times(1)).findByProductId(anyString());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testAddInventory_ExistingProduct() {
        when(inventoryRepository.findByProductId(anyString())).thenReturn(inventory);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        InventoryDto result = inventoryService.addInventory(inventoryDto);
        assertNotNull(result);
        assertEquals(inventoryDto.getProductId(), result.getProductId());
        verify(inventoryRepository, times(1)).findByProductId(anyString());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testUpdateInventory() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);
        InventoryDto result = inventoryService.updateInventory(1L, inventoryDto);
        assertNotNull(result);
        assertEquals(inventoryDto.getProductId(), result.getProductId());
        verify(inventoryRepository, times(1)).findById(anyLong());
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    void testDeleteInventoryById() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(inventory));
        doNothing().when(inventoryRepository).delete(any(Inventory.class));
        inventoryService.deleteInventoryById(1L);
        verify(inventoryRepository, times(1)).findById(anyLong());
        verify(inventoryRepository, times(1)).delete(any(Inventory.class));
    }

    @Test
    void testGetInventoryById() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(inventory));
        InventoryDto result = inventoryService.getInventoryById(1L);
        assertNotNull(result);
        assertEquals(inventoryDto.getProductId(), result.getProductId());
        verify(inventoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAllInventories() {
        when(inventoryRepository.findAll()).thenReturn(List.of(inventory));
        List<InventoryDto> result = inventoryService.getAllInventories();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetInventoryByProductId() {
        when(inventoryRepository.findByProductId(anyString())).thenReturn(inventory);
        InventoryDto result = inventoryService.getInventoryByProductId("Product1");
        assertNotNull(result);
        assertEquals(inventoryDto.getProductId(), result.getProductId());
        verify(inventoryRepository, times(1)).findByProductId(anyString());
    }

    @Test
    void testGetInventoryById_NotFound() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InventoryNotFoundException.class, () -> inventoryService.getInventoryById(1L));
        verify(inventoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateInventory_NotFound() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InventoryNotFoundException.class, () -> inventoryService.updateInventory(1L, inventoryDto));
        verify(inventoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteInventoryById_NotFound() {
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InventoryNotFoundException.class, () -> inventoryService.deleteInventoryById(1L));
        verify(inventoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetInventoryByProductId_NotFound() {
        when(inventoryRepository.findByProductId(anyString())).thenReturn(null);
        assertThrows(InventoryNotFoundException.class, () -> inventoryService.getInventoryByProductId("Product1"));
        verify(inventoryRepository, times(1)).findByProductId(anyString());
    }
}