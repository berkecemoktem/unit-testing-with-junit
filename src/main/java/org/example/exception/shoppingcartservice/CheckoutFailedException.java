package org.example.exception.shoppingcartservice;

public class CheckoutFailedException extends RuntimeException {
    public CheckoutFailedException(String message) {
        super(message);
    }
}

