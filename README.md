# Unit Testing with JUnit

This repository serves as a basic demonstration of writing unit tests and test cases using JUnit for a simple shopping application.

## Overview

The provided codebase comprises Java classes organized into various packages such as exception, model, and service. It appears to be a simple e-commerce application with functionalities related to managing shopping carts, customer credentials, and processing checkout operations.

### Tech Stack

- Java 21
- JUnit 4.13.1
- JUnit Jupiter API 5.10.0
- Mockito Core 2.21.0
- Maven

## Exception Layer

### CheckoutFailedException

This custom exception class extends RuntimeException, providing a constructor that accepts a message string. It's used to indicate failures during the checkout process and adheres to Java best practices for custom exception handling.

## Model Layer

### CustomerCredentials

Represents customer credentials with attributes for email, password, and encrypted password. It follows the JavaBean conventions with getter and setter methods for each attribute. Lombok annotations @Getter and @Setter are used to generate getters and setters automatically.

### Product

Represents a product with attributes such as ID, name, price, and stock. Overrides equals() and hashCode() methods to compare products based on their IDs. Lombok annotations are used for automatic getter and setter generation.

### ShoppingCart

Represents a shopping cart containing a list of shopping cart items. Provides a constructor to initialize the shopping cart with an empty list of items. Lombok annotations are used for automatic getter and setter generation.

### ShoppingCartItem

Represents an item in the shopping cart with attributes for the product, quantity, and validity status. It follows JavaBean conventions with getter and setter methods. Lombok annotations are used for automatic getter and setter generation.

## Service Layer

### CustomerCredentialsService

An interface defining methods for validating customer credentials and encrypting passwords.

### CustomerCredentialsServiceImpl

Implements the CustomerCredentialsService interface, providing concrete implementations for credential validation and password encryption. The isCustomerValid method checks if the provided credentials meet certain criteria (e.g., password length and email domain). The encryptCredentials method hashes the provided password using SHA-256 and updates the encrypted password attribute of the CustomerCredentials object.

### ShoppingCartService

An interface defining a method for processing checkout operations.

### ShoppingCartServiceImpl

Implements the ShoppingCartService interface, providing functionality to process checkout operations. The processCheckout method validates the shopping cart and customer credentials before calculating the total amount. It throws CheckoutFailedException if the checkout fails due to invalid credentials or an invalid cart.

### ShoppingService

An interface defining methods for adding/removing items from the shopping cart, calculating total prices, and validating the cart.

### ShoppingServiceImpl

Implements the ShoppingService interface, providing concrete implementations for adding/removing items from the cart, calculating the total price, and validating the cart. It includes methods to add and remove items from the cart, calculate the total price (with a simulated delay), and validate the cart based on quantity limits.

## Testing Report

### Introduction

The testing focused on ensuring the correctness and reliability of the implemented functionalities. The project uses the following technologies for testing:
1. JUnit 4.13.1
2. JUnit Jupiter API 5.10.0
3. Mockito Core 2.21.0

### Test Suite Overview

Two test suites were created to organize the tests effectively:
1. **CredentialsTestSuit**: This suite contains tests related to the CustomerCredentialsServiceImpl class.
2. **ShoppingSuit**: This suite contains tests related to the shopping functionalities, including ShoppingServiceImpl and ShoppingCartServiceImpl.

### Test Coverage

The tests comprehensively cover various scenarios and edge cases to ensure the robustness of the implemented functionalities. Below are the key areas covered by the tests:

1. **Customer Credentials Service**:
   - Validating customer credentials for different email formats.
   - Encrypting customer passwords and handling null or empty credentials.

2. **Shopping Service**:
   - Adding products to the shopping cart, including cases where the product already exists or the quantity is invalid.
   - Removing products from the cart, considering scenarios where the product is not found or the requested quantity exceeds the available quantity.
   - Calculating the total price of items in the cart, including tests with a timeout to simulate time-consuming operations.
   - Validating the shopping cart, ensuring it meets certain criteria such as the total quantity limit.

3. **Shopping Cart Service**:
   - Processing checkout successfully with valid cart and credentials.
   - Handling checkout failures due to an invalid cart or credentials.

### Testing Methodology

- **Mocking Dependencies**: Mockito framework was used to mock dependencies such as database access and external services, ensuring isolated unit testing.
- **Test Data**: Various test data were used to cover different scenarios, including valid and invalid inputs.
- **Exception Handling**: Tests were designed to verify the proper handling of exceptions in exceptional scenarios.
- **Timeout Testing**: Some tests included timeouts to ensure that time-consuming operations did not exceed the expected duration.


## Code Coverage

![](/Users/berkecemoktem/Desktop/code-coverage.png)

NOTE: ‘Model’ and ‘Exception’ Layer classes are excluded in the test run configurations. Only the service classes have been targeted to be tested. You can see the class, method, and line coverage details in the above report.

## UML DIAGRAM

![](/Users/berkecemoktem/Downloads/unitTestingUML.drawio.png)