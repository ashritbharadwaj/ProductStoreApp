package com.productstore.inventory;

import com.productstore.inventory.entity.Inventory;
import com.productstore.inventory.repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryApplication implements CommandLineRunner {

	@Autowired
	private InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		inventoryRepository.save(new Inventory("1L", 1));
		inventoryRepository.save(new Inventory("2L", 2));
		inventoryRepository.save(new Inventory("3L", 3));
	}
}
