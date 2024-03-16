package org.example.service;

import org.example.model.CustomerCredentials;
import org.example.model.ShoppingCart;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ShoppingService shoppingService;
    private CustomerCredentialsService customerCredentialsService;

    public ShoppingCartServiceImpl(ShoppingService shoppingService, CustomerCredentialsService credentialsService) {
        this.shoppingService = shoppingService;
        this.customerCredentialsService = credentialsService;
    }

    @Override
    public boolean processCheckout(ShoppingCart cart, CustomerCredentials credentials) {
        // Implementation to process the checkout, e.g., validate cart and calculate total
        if (shoppingService.validateCart(cart)) {
            if(customerCredentialsService.isCustomerValid(credentials)){
                double total = shoppingService.calculateTotal(cart);
                System.out.println("Checkout successful for the user: " + credentials.getMail() + " -> Total amount: $" + total);
                return true;
            }
            else {
                System.out.println("Checkout failed.");
                return false;
            }
        }
            System.out.println("Checkout failed.");
        return false;
    }
}
