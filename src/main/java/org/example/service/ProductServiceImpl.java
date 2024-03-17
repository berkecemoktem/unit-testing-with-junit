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
}
