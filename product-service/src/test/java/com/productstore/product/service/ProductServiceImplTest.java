package com.productstore.product.service;

import com.productstore.product.dto.ProductDto;
import com.productstore.product.entity.Product;
import com.productstore.product.repo.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.productstore.product.exceptions.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        product = new Product("1", "Product1", 100.0, "Category1", "Description1");
        productDto = new ProductDto("1", "Product1", 100.0, "Category1", "Description1");
    }

    @Test
    void testAddProductShouldReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto result = productService.addProduct(productDto);
        assertNotNull(result);
        assertEquals(productDto.getName(), result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductShouldReturnProduct() {
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto result = productService.updateProduct("1", productDto);
        assertNotNull(result);
        assertEquals(productDto.getName(), result.getName());
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProductById() {
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));
        productService.deleteProductById("1");
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        ProductDto result = productService.getProductById("1");
        assertNotNull(result);
        assertEquals(productDto.getName(), result.getName());
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<ProductDto> result = productService.getAllProducts();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById("1"));
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct("1", productDto));
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testDeleteProductById_NotFound() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById("1"));
        verify(productRepository, times(1)).findById(anyString());
    }
}