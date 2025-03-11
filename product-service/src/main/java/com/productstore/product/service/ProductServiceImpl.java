package com.productstore.product.service;

import com.productstore.product.dto.ProductDto;
import com.productstore.product.entity.Product;
import com.productstore.product.exceptions.ProductNotFoundException;
import com.productstore.product.repo.ProductRepository;
import com.productstore.product.service.aspects.Loggable;
import com.productstore.product.utils.ProductTypeConversion;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        log.info("Adding product: {}", productDto);
        Product product = ProductTypeConversion.productDtoToProduct(productDto);
        productRepository.save(product);
        log.info("Product added successfully: {}", product);
        return ProductTypeConversion.productToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        log.info("Updating product with id: {}", productId);
        productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        productDto.setProductId(productId);
        Product product = ProductTypeConversion.productDtoToProduct(productDto);
        productRepository.save(product);
        log.info("Product updated successfully: {}", product);
        return ProductTypeConversion.productToProductDto(product);
    }

    @Override
    public void deleteProductById(String productId) {
        log.info("Deleting product with id: {}", productId);
        productRepository.delete(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId)));
        log.info("Product deleted successfully with id: {}", productId);
    }

    @Override
    public ProductDto getProductById(String productId) {
        log.info("Fetching product with id: {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        log.info("Product fetched successfully: {}", product);
        return ProductTypeConversion.productToProductDto(product);
    }

    @Loggable
    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()) {
            log.info("Products fetched successfully");
            return products.stream().map(ProductTypeConversion::productToProductDto).toList();
        }
        log.info("No products found");
        return List.of();
    }
}