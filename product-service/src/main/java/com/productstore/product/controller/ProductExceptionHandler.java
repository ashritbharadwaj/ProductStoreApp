package com.productstore.product.controller;

import com.productstore.product.dto.ErrorDetailsDTO;
import com.productstore.product.exceptions.ProductNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleProductNotFoundException(ProductNotFoundException ex) {
        ErrorDetailsDTO errorDetails = ErrorDetailsDTO.builder()
                .errorMessage(ex.getMessage())
                .errorCode(404)
                .timestamp(LocalDateTime.now())
                .toContact("ProductNotFoundSupport").build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetailsDTO> handle400(MethodArgumentNotValidException ex){
        String errorMessage= ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorDetailsDTO errorDetails=
                ErrorDetailsDTO.builder().errorCode(400)
                        .timestamp(java.time.LocalDateTime.now())
                        .toContact("MethodArgumentNotValidExceptionSupport")
                        .errorMessage(errorMessage).build();

        return ResponseEntity.badRequest().body(errorDetails);
    }
}
