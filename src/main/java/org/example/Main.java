package org.example;

import org.example.model.CustomerCredentials;
import org.example.model.Product;
import org.example.model.ShoppingCart;
import org.example.model.ShoppingCartItem;
import org.example.service.*;

public class Main {
    public static CustomerCredentials credentials;
    public static CustomerCredentialsService customerCredentialsService;
    public static Product laptop;
    public static Product smartphone;
    public static ShoppingService shoppingService;
    public static ShoppingCartService shoppingCartService;
    public static ShoppingCart cart;

    public static void main(String[] args) {

        //Initialize the credentials
        credentials = new CustomerCredentials("example-user-mail@gmail.com",
                "aaabbbccc");
        customerCredentialsService = new CustomerCredentialsServiceImpl();
        customerCredentialsService.encryptCredentials(credentials);

        //Instantiate necessary objects and services
        laptop = new Product("P001", "Laptop", 999.99, 5,5.0);
        smartphone = new Product("P002", "Smartphone", 499.99, 10, 1.0);

        shoppingService = new ShoppingServiceImpl();
        shoppingCartService = new ShoppingCartServiceImpl(shoppingService, customerCredentialsService);

        // Create a shopping cart
        cart = new ShoppingCart();

        // Print initial product stock
        System.out.println("Initial product stock:");
        printProductStock(laptop);
        printProductStock(smartphone);

        // Add products to the cart
        shoppingService.addToCart(cart, laptop, 2);
        shoppingService.addToCart(cart, smartphone, 1);

        // Process checkout
        shoppingCartService.processCheckout(cart, credentials);

        // Print final product stock after checkout
        System.out.println("Final product stock after checkout:");
        printProductStock(laptop);
        printProductStock(smartphone);

        displayCart(cart);

        // Remove 1 laptop from the cart
        try {
            shoppingService.removeFromCart(cart, laptop, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //Print updated cart state
        System.out.println("Updated cart state:");
        displayCart(cart);
    }

    private static void printProductStock(Product product) {
        System.out.println(product.getName() + " Stock: " + product.getStock());
    }

    private static void displayCart(ShoppingCart cart) {
        System.out.println("***Cart Items***");
        for (ShoppingCartItem item : cart.getItems()) {
            System.out.println(item.getProduct().getName() + " -> Quantity: " + item.getQuantity());
        }
        System.out.println();
    }
}
