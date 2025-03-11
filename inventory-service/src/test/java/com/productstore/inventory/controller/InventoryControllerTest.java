package com.productstore.inventory.controller;

import com.productstore.inventory.dto.InventoryDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

//    @Test
//    void getInventoryById() {
//        // Add an inventory to test retrieval
//        InventoryDto inventoryDto = getInventoryRequest();
//        Integer inventoryId = RestAssured.given()
//                .contentType("application/json")
//                .body(inventoryDto)
//                .when()
//                .post("/api/inventories")
//                .then()
//                .statusCode(201)
//                .extract()
//                .path("id");
//
//        // Retrieve the inventory by ID and validate fields
//        RestAssured.given()
//                .contentType("application/json")
//                .when()
//                .get("/api/inventories/" + inventoryId)
//                .then()
//                .statusCode(200)
//                .body("id", equalTo(inventoryId))
//                .body("productId", equalTo(inventoryDto.getProductId()))
//                .body("quantity", equalTo(inventoryDto.getQuantity()));
//    }

    @Test
    void getAllInventories() {
        // Add two test inventories
        InventoryDto inventory1 = new InventoryDto("Product1", 100);
        InventoryDto inventory2 = new InventoryDto("Product2", 200);

        RestAssured.given().contentType("application/json").body(inventory1).post("/api/inventories").then().statusCode(201);
        RestAssured.given().contentType("application/json").body(inventory2).post("/api/inventories").then().statusCode(201);

        // Verify all inventories are returned
        RestAssured.given()
                .when()
                .get("/api/inventories")
                .then()
                .statusCode(200)
                .body(".", hasSize(greaterThanOrEqualTo(2)))
                .body("productId", hasItems("Product1", "Product2"))
                .body("quantity", hasItems(100, 200));
    }

    @Test
    void deleteInventoryById() {
        // Add and extract inventory ID
        InventoryDto inventoryDto = getInventoryRequest();
        Integer inventoryId = RestAssured.given()
                .contentType("application/json")
                .body(inventoryDto)
                .post("/api/inventories")
                .then()
                .statusCode(201)
                .extract().path("id");

        // Delete the inventory
        RestAssured.given()
                .contentType("application/json")
                .when()
                .delete("/api/inventories/" + inventoryId)
                .then()
                .statusCode(204);

        // Verify deletion
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/inventories/" + inventoryId)
                .then()
                .statusCode(404);
    }

    @Test
    void updateInventory() {
        // Add initial inventory
        InventoryDto inventoryDto = getInventoryRequest();
        Integer inventoryId = RestAssured.given()
                .contentType("application/json")
                .body(inventoryDto)
                .post("/api/inventories")
                .then()
                .statusCode(201)
                .extract().path("id");

        // Create updated inventory data
        InventoryDto updatedInventory = new InventoryDto("Product1", 300);

        // Perform update
        RestAssured.given()
                .contentType("application/json")
                .body(updatedInventory)
                .when()
                .put("/api/inventories/" + inventoryId)
                .then()
                .statusCode(200);

        // Verify updated fields
        RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/inventories/" + inventoryId)
                .then()
                .statusCode(200)
                .body("productId", equalTo(updatedInventory.getProductId()))
                .body("quantity", equalTo(updatedInventory.getQuantity()));
    }

//    @Test
//    void addInventory() {
//        InventoryDto inventoryDto = getInventoryRequest();
//        RestAssured.given()
//                .contentType("application/json")
//                .body(inventoryDto)
//                .when()
//                .post("/api/inventories")
//                .then()
//                .log().all()
//                .statusCode(201)
//                .body("id", notNullValue())
//                .body("productId", equalTo(inventoryDto.getProductId()))
//                .body("quantity", equalTo(inventoryDto.getQuantity()));
//    }

    private InventoryDto getInventoryRequest() {
        return new InventoryDto("Product1", 100);
    }
}