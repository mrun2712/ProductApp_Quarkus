# ProductApp_Quarkus

Product Management System - Quarkus REST API
This project implements a simple Product Management System using Quarkus, allowing basic CRUD operations on products. It also features endpoints for stock availability checking and retrieving products sorted by price.

Features
CRUD Operations: Create, Read, Update, and Delete products.
Stock Availability Check: Verify whether a product's stock can fulfill a requested quantity.
Sort Products by Price: Retrieve all products sorted by price (ascending).

Technologies Used
Quarkus: Framework for building Java applications.
JPA: For database persistence.
H2: In-memory database.
RESTEasy: For building RESTful APIs.
Swagger: API documentation and testing.

Prerequisites:
Java 11 or higher
Maven 3.6.3+
IDE with Maven support (Optional)

Getting Started

1. Build the application
   bash
   Copy code
   ./mvnw clean package
2. Run the application in dev mode
   bash
   Copy code
   ./mvnw quarkus:dev
   The server will start at http://localhost:8080.

Database Configuration
The application uses an H2 in-memory database. By default, the database will persist data for the duration of the session.

To access the H2 console:
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:product_db
Username: sa
Password: sa
API Documentation (Swagger UI)
The Swagger UI provides a visual interface for testing the APIs. After running the project, access the Swagger interface at:

bash
Copy code
http://localhost:8080/q/swagger-ui
Example Test Data
Use the following JSON objects to test the APIs:

Create a Product
json
Copy code
{
"name": "Laptop",
"description": "Gaming Laptop",
"price": 1500.00,
"quantity": 10
}

Check Stock Availability (by Product ID)
Endpoint: /products/{id}/availability?count=5
Also, /products/{id}/availability?count=20
Replace {id} with the product ID.
Pass the desired quantity as the count parameter.
Retrieve Products Sorted by Price
Endpoint: /products/sorted-by-price
Retrieve Products Sorted by Price Descending order
Endpoint: /products/descending-sort

Running Tests
To run the tests, use the following command:

bash
Copy code
./mvnw test
This will run the unit tests, ensuring that all API functionality works as expected.
