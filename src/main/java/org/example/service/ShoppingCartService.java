package org.example.service;

import org.example.model.CustomerCredentials;
import org.example.model.ShoppingCart;

public interface ShoppingCartService {
    public boolean processCheckout(ShoppingCart cart, CustomerCredentials credentials) ;
}
