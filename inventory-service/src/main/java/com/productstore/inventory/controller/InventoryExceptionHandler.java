package com.productstore.inventory.controller;

import com.productstore.inventory.dto.ErrorDetailsDTO;
import com.productstore.inventory.exceptions.InventoryNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class InventoryExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        ErrorDetailsDTO errorDetails = ErrorDetailsDTO.builder()
                .errorMessage(ex.getMessage())
                .errorCode(404)
                .timestamp(java.time.LocalDateTime.now())
                .toContact("InventoryNotFoundSupport")
                .build();
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
