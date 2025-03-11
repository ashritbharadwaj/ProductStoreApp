package com.productstore.product.service;

import com.productstore.product.dto.ProductDto;

import java.util.List;

public interface ProductService {

    public ProductDto addProduct(ProductDto productDto);

    public ProductDto updateProduct(String productId,ProductDto productDto);

    public void deleteProductById(String productId);

    public ProductDto getProductById(String productId);

    public List<ProductDto> getAllProducts();
}
