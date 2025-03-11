package com.productstore.inventory.repo;

import com.productstore.inventory.entity.Inventory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProductId(@NotNull(message = "Product ID is required") String productId);
}
