package com.productstore.product.utils;

//import com.txn.transaction.dto.TransactionDTO;
//import com.txn.transaction.entities.Transaction;

import com.productstore.product.dto.ProductDto;
import com.productstore.product.entity.Product;

public class ProductTypeConversion {
    public static Product productDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
//        product.setCodeNumber(productDto.getCodeNumber());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        return product;
    }

    public static ProductDto productToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
//        productDto.setCodeNumber(product.getCodeNumber());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory());
        productDto.setDescription(product.getDescription());
        return productDto;
    }
}
