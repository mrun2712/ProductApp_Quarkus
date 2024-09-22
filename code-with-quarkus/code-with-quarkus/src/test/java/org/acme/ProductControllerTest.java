package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ProductControllerTest {

    // Test Data
    private static final String PRODUCT_JSON = """
        {
            "name": "Laptop",
            "description": "Gaming Laptop",
            "price": 1200.00,
            "quantity": 5
        }
    """;

    @Test
    public void testGetAllProducts() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testGetProductById() {
        // First, create a product to get by ID
        int productId = given()
                .body(PRODUCT_JSON)
                .header("Content-Type", "application/json")
                .when().post("/products")
                .then()
                .statusCode(201)
                .extract().path("id");

        // Now, test retrieving the product by ID
        given()
                .when().get("/products/" + productId)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("name", is("Laptop"));
    }

    @Test
    public void testCreateProduct() {
        given()
                .body(PRODUCT_JSON)
                .header("Content-Type", "application/json")
                .when().post("/products")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON)
                .body("name", is("Laptop"))
                .body("description", is("Gaming Laptop"));
    }

    @Test
    public void testUpdateProduct() {
        // First, create a product to update
        int productId = given()
                .body(PRODUCT_JSON)
                .header("Content-Type", "application/json")
                .when().post("/products")
                .then()
                .statusCode(201)
                .extract().path("id");

        // Define the updated product JSON
        String updatedProductJson = """
        {
            "name": "Updated Laptop",
            "description": "Updated Gaming Laptop",
            "price": 1500.00,
            "quantity": 10
        }
        """;

        // Test updating the product
        given()
                .body(updatedProductJson)
                .header("Content-Type", "application/json")
                .when().put("/products/" + productId)
                .then()
                .statusCode(200)
                .body("name", is("Updated Laptop"))
                .body("description", is("Updated Gaming Laptop"))
                .body("price", is(1500.00f))
                .body("quantity", is(10));
    }

    @Test
    public void testDeleteProduct() {
        // First, create a product to delete
        int productId = given()
                .body(PRODUCT_JSON)
                .header("Content-Type", "application/json")
                .when().post("/products")
                .then()
                .statusCode(201)
                .extract().path("id");

        // Test deleting the product
        given()
                .when().delete("/products/" + productId)
                .then()
                .statusCode(200); // No Content
    }

    @Test
    public void testCheckStockAvailability() {
        // First, create a product
        int productId = given()
                .body(PRODUCT_JSON)
                .header("Content-Type", "application/json")
                .when().post("/products")
                .then()
                .statusCode(201)
                .extract().path("id");

        // Test availability with count less than stock
        given()
                .when().get("/products/availability/" + productId + "?count=3")
                .then()
                .statusCode(200)
                .body(is("true"));

        // Test availability with count more than stock
        given()
                .when().get("/products/availability/" + productId + "?count=6")
                .then()
                .statusCode(200)
                .body(is("false"));
    }

    @Test
    public void testGetProductsSortedByPrice() {
        String product1Json = """
    {
        "name": "Laptop",
        "description": "Gaming Laptop",
        "price": 1500.00,
        "quantity": 10
    }
    """;

        String product2Json = """
    {
        "name": "Mouse",
        "description": "Gaming Mouse",
        "price": 50.00,
        "quantity": 20
    }
    """;

        String product3Json = """
    {
        "name": "Keyboard",
        "description": "Mechanical Keyboard",
        "price": 100.00,
        "quantity": 15
    }
    """;

        given().body(product1Json).header("Content-Type", "application/json").when().post("/products").then().statusCode(201);
        given().body(product2Json).header("Content-Type", "application/json").when().post("/products").then().statusCode(201);
        given().body(product3Json).header("Content-Type", "application/json").when().post("/products").then().statusCode(201);

        // Test sorting by price
        given()
                .when().get("/products/sorted-by-price")
                .then()
                .statusCode(200)
                .body("[0].name", is("Mouse"))    // Lowest price first
                .body("[1].name", is("Keyboard")) // Next higher price
                .body("[2].name", is("Laptop"));  // Highest price last
    }


}


