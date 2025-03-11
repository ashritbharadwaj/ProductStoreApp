package com.productstore.product.controller;

import com.productstore.product.dto.ProductDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.org.checkerframework.checker.i18n.qual.LocalizableKey;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProduct() {
        // Add a product to test retrieval
        ProductDto productDto = getProductRequest();
        String productId = RestAssured.given()
                .contentType("application/json")
                .body(productDto)
                .when()
                .post("/api/products")
                .then()
                .statusCode(201)
                .extract()  // Extract the auto-generated product ID
                .path("productId");

        // Retrieve the product by ID and validate fields
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/products/" + productId)
                .then()
                .statusCode(200)
                .body("productId", equalTo(productId))
                .body("name", equalTo(productDto.getName()))
                .body("price", is((float) productDto.getPrice()))  // Match Float type
                .body("category", equalTo(productDto.getCategory()))
                .body("description", equalTo(productDto.getDescription()));
    }

    @Test
    void getAllProducts() {
        // Add two test products
        ProductDto product1 = new ProductDto("Keyboard", 50.0, "Electronics", "Mechanical keyboard");
        ProductDto product2 = new ProductDto("Mouse", 25.0, "Electronics", "Wireless mouse");

        RestAssured.given().contentType("application/json").body(product1).post("/api/products").then().statusCode(201);
        RestAssured.given().contentType("application/json").body(product2).post("/api/products").then().statusCode(201);

        // Verify all products are returned
        RestAssured.given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body(".", hasSize(greaterThanOrEqualTo(2)))  // Check at least 2 items
                .body("name", hasItems("Keyboard", "Mouse"))  // Verify names exist
                .body("price", hasItems(50.0f, 25.0f))  // Check prices as Floats
                .body("category", hasItem(equalTo("Electronics")) );  // All items in category
    }

    @Test
    void deleteProduct() {
        // Add and extract product ID
        ProductDto productDto = getProductRequest();
        String productId = RestAssured.given()
                .contentType("application/json")
                .body(productDto)
                .post("/api/products")
                .then()
                .statusCode(201)
                .extract().path("productId");

        // Delete the product
        RestAssured.given()
                .contentType("application/json")
                .when()
                .delete("/api/products/" + productId)
                .then()
                .statusCode(204);  // 204 No Content

        // Verify deletion
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/products/" + productId)
                .then()
                .statusCode(404);  // 404 Not Found
    }

    @Test
    void updateProduct() {
        // Add initial product
        ProductDto productDto = getProductRequest();
        String productId = RestAssured.given()
                .contentType("application/json")
                .body(productDto)
                .post("/api/products")
                .then()
                .statusCode(201)
                .extract().path("productId");

        // Create updated product data
        ProductDto updatedProduct = new ProductDto("Updated Name", 300.0, "Updated Category", "New Description");

        // Perform update
        RestAssured.given()
                .contentType("application/json")
                .body(updatedProduct)
                .when()
                .put("/api/products/" + productId)
                .then()
                .statusCode(202);  // 202 Accepted

        // Verify updated fields
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/products/" + productId)
                .then()
                .statusCode(200)
                .body("name", equalTo(updatedProduct.getName()))
                .body("price", is((float) updatedProduct.getPrice()))
                .body("category", equalTo(updatedProduct.getCategory()))
                .body("description", equalTo(updatedProduct.getDescription()));
    }

    @Test
    void addProduct() {
        ProductDto productDto = getProductRequest();
        RestAssured.given()
                .contentType("application/json")
                .body(productDto)
                .when()
                .post("/api/products")
                .then()
                .log().all()
                .statusCode(201)
                .body("productId", notNullValue())
                .body("name", equalTo(productDto.getName()))
                // Cast expected value to float and use float delta
                .body("price", is((float) productDto.getPrice()))
                .body("category", is(productDto.getCategory()))
                .body("description", is(productDto.getDescription()));
    }

    private ProductDto getProductRequest(){
        return new ProductDto("name1", 200, "category1", "description1");
    }
}