package org.example.service;

import org.example.model.Product;
import org.example.model.ShoppingCart;
import org.example.model.ShoppingCartItem;

public interface ShoppingService {
    void addToCart(ShoppingCart cart, Product product, int quantity);


    void removeFromCart(ShoppingCart cart, Product product, int quantityToRemove);

    void removeFromCartMutated(ShoppingCart cart, Product product, int quantityToRemove);

    ShoppingCartItem getCartItemByName(ShoppingCart cart, String productName);

    double calculateTotal(ShoppingCart cart);

    boolean validateCart(ShoppingCart cart);
}
