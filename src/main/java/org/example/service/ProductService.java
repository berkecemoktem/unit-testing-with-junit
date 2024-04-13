package org.example.service;

import org.example.model.Product;

public interface ProductService {
    double calculateDiscountedPrice(Product product, double discountPercentage);

    boolean isEligibleForFreeShipping(Product product);
}
