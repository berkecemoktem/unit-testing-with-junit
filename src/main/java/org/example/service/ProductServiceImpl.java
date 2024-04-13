package org.example.service;

import org.example.model.Product;

public class ProductServiceImpl implements ProductService{
    @Override
    public double calculateDiscountedPrice(Product product, double discountPercentage) {
        double originalPrice = product.getPrice();
        double discountAmount = originalPrice * (discountPercentage / 100.0);
        double discountedPrice = originalPrice - discountAmount;
        return discountedPrice;
    }

    @Override
    public boolean isEligibleForFreeShipping(Product product) {
        // Criteria 1: Price is greater than $50
        if (product.getPrice() > 50) { // Complexity +1
            // Criteria 2: Stock is greater than 0
            if (product.getStock() > 0) { // Complexity +1
                // Criteria 3: Product name does not contain "fragile"
                if (!product.getName().toLowerCase().contains("fragile")) { // Complexity +1
                    // Criteria 4: Product weight is less than or equal to 10
                    if (product.getWeight() <= 10) { // Complexity +1
                        return true; // Eligible for free shipping
                    }
                }
            }
        }
        return false; // Not eligible for free shipping
    }

    //M = E - N + 2P
    /*
    where E = the number of edges in the control flow graph
    N = the number of nodes in the control flow graph
    P = the number of connected components
    */

    //Result = 4

}
