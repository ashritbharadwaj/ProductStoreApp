package com.productstore.product;

import com.productstore.product.entity.Product;
import com.productstore.product.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		productRepository.save(new Product("1L", "name1", 1.0, "category1", "description1"));
		productRepository.save(new Product( "2L","name2", 2.0, "category2", "description2"));
		productRepository.save(new Product( "3L","name3", 3.0, "category3", "description3"));
	}
}
