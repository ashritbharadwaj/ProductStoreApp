package com.productstore.order.controller;

import com.productstore.order.controller.stubs.ProductClientStub;
import io.restassured.RestAssured;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class OrderControllerTest {

    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }

    @Test
    public void getProductById() {
        String ProductJson = """
                {
                    "productId": "1",
                    "name": "Product 1",
                    "price": 10.99,
                    "category": "Category 1",
                    "description": "Description 1"
                }
                """;

        ProductClientStub.stubProductCall("1");

        var responseBodyString = RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/api/orders")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();

    }
}