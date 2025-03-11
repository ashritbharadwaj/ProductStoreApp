package com.productstore.inventory.exceptions;

public class InventoryNotFoundException extends RuntimeException {
  public InventoryNotFoundException(String message) {
    super(message);
  }
}
