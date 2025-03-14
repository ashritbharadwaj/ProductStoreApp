package com.productstore.order.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productstore.order.dto.ErrorDetailsDTO;
import com.productstore.order.exceptions.OrderCannotCreatedException;
import com.productstore.order.exceptions.OrderNotFoundException;
import feign.FeignException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleOrderNotFoundException(OrderNotFoundException ex) {
//        return ErrorDetailsDTO.builder().timestamp(System.currentTimeMillis()).message(ex.getMessage()).build();
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsDTO.builder()
                .timestamp(LocalDateTime.now())
                .errorMessage(ex.getMessage())
                .errorCode(404)
                .toContact("OrderNotFoundSupport")
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetailsDTO);
    }

    @ExceptionHandler(OrderCannotCreatedException.class)
    public ResponseEntity<ErrorDetailsDTO> handleOrderCannotCreatedException(OrderCannotCreatedException ex) {
        ErrorDetailsDTO errorDetailsDTO = ErrorDetailsDTO.builder()
                .timestamp(LocalDateTime.now())
                .errorMessage(ex.getMessage())
                .errorCode(400)
                .toContact("OrderCannotCreatedSupport")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetailsDTO);
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

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorDetailsDTO> handleFeignNotFoundException(FeignException.NotFound ex) {
        String exceptionMessage = ex.getMessage();
        int startIndex = exceptionMessage.indexOf("[{")+1;
        int endIndex = exceptionMessage.indexOf("}]")+1;
        String jsonPart = exceptionMessage.substring(startIndex, endIndex);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonPart);
        } catch (Exception e) {
            // Handle parsing error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String errorMessage = jsonNode.has("errorMessage") ? jsonNode.get("errorMessage").asText() : "Unknown error";
        String timestamp = jsonNode.has("timestamp") ? jsonNode.get("timestamp").asText() : LocalDateTime.now().toString();
        int errorCode = jsonNode.has("errorCode") ? jsonNode.get("errorCode").asInt() : 500;
        String toContact = jsonNode.has("toContact") ? jsonNode.get("toContact").asText() : "Unknown contact";

        ErrorDetailsDTO errorDetails = ErrorDetailsDTO.builder()
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.parse(timestamp))
                .toContact(toContact)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}
