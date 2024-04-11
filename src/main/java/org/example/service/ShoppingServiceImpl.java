package org.example.service;


import org.example.model.Product;
import org.example.model.ShoppingCart;
import org.example.model.ShoppingCartItem;

import java.util.NoSuchElementException;
import java.util.Optional;

public class ShoppingServiceImpl implements ShoppingService{
    @Override
    public void addToCart(ShoppingCart cart, Product product, int quantity) {
        if (quantity > 0 && product.getStock() >= quantity) {
            // Check if the product already exists in the cart
            Optional<ShoppingCartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().equals(product))
                    .findFirst();

            if (existingItem.isPresent()) {
                // If the product exists, update the quantity
                existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
                System.out.println("Quantity updated for product '" + product.getName() +
                        "' in the cart. New quantity: " + existingItem.get().getQuantity());
            } else {
                // If the product is not in the cart, add a new item
                ShoppingCartItem newItem = new ShoppingCartItem(product, quantity, true);
                cart.getItems().add(newItem);
                System.out.println("Product '" + product.getName() + "' added to the cart with quantity: " + quantity);
            }

            // Update the stock of the product
            product.setStock(product.getStock() - quantity);
            System.out.println("Product '" + product.getName() + "' stock updated. New stock: " + product.getStock());
        } else {
            throw new IllegalArgumentException("Invalid quantity or insufficient stock.");
        }
    }


    @Override
    public void removeFromCart(ShoppingCart cart, Product product, int quantityToRemove) {
        // Find the item in the cart based on the product
        Optional<ShoppingCartItem> itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (itemToRemove.isPresent()) {
            int existingQuantity = itemToRemove.get().getQuantity();

            // Check if the requested quantity is valid
            if (quantityToRemove > existingQuantity) {
                throw new IllegalArgumentException("Requested quantity exceeds the quantity of the product in the cart.");
            }

            // Update the stock of the product
            product.setStock(product.getStock() + quantityToRemove);
            System.out.println("Product '" + product.getName() + "' stock updated. New stock: " + product.getStock());

            // Update the quantity of the existing item in the cart
            itemToRemove.get().setQuantity(existingQuantity - quantityToRemove);

            // Remove the item from the cart if its quantity becomes zero or less
            if (itemToRemove.get().getQuantity() <= 0) {
                cart.getItems().remove(itemToRemove.get());
            }

            System.out.println("Removed " + quantityToRemove + " '" + product.getName() + "' from the cart.");
        } else {
            throw new NoSuchElementException("Product not found in the cart.");
        }
    }

    /***************MUTATED CODES***************/
    @Override
    public void removeFromCartMutated(ShoppingCart cart, Product product, int quantityToRemove) {
        // Mutation 1: Negating a condition
        // Original condition: if (itemToRemove.isPresent())
        // Mutated condition: if (!itemToRemove.isPresent())
        Optional<ShoppingCartItem> itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (!itemToRemove.isPresent()) { // Mutation 1
            throw new NoSuchElementException("Product not found in the cart.");
        }

        int existingQuantity = itemToRemove.get().getQuantity();

        // Mutation 2: Changing a constant value
        // Original error message: "Requested quantity exceeds the quantity of the product in the cart."
        // Mutated error message: "Quantity requested is not valid."
        if (quantityToRemove > existingQuantity) { // Mutation 2
            throw new IllegalArgumentException("Quantity requested is not valid."); // Mutation 2
        }

        // Mutation 3: Removing a method call
        // Original call: product.setStock(product.getStock() + quantityToRemove);
        // Mutated code: Removed the call to product.setStock()
        // product.setStock(product.getStock() + quantityToRemove); // Mutation 3

        // Mutation 4: Changing the method call parameter
        // Original call: itemToRemove.get().setQuantity(existingQuantity - quantityToRemove);
        // Mutated code: itemToRemove.get().setQuantity(existingQuantity + quantityToRemove);
        itemToRemove.get().setQuantity(existingQuantity + quantityToRemove); // Mutation 4

        // Remove the item from the cart if its quantity becomes zero or less
        if (itemToRemove.get().getQuantity() <= 0) {
            cart.getItems().remove(itemToRemove.get());
        }

        System.out.println("Removed " + quantityToRemove + " '" + product.getName() + "' from the cart.");
    }



    @Override
    public ShoppingCartItem getCartItemByName(ShoppingCart cart, String productName) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getName().equals(productName))
                .findFirst()
                .orElse(null);
    }


    @Override
    public double calculateTotal(ShoppingCart cart) {
        System.out.println("wait for 3 seconds for the final price...");
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        try {
            // Sleep for 3 seconds to simulate a time-consuming operation
            Thread.sleep(3000);
            System.out.println("Total price calculated for the items in the cart: $" + total);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return total;
    }

    @Override
    public boolean validateCart(ShoppingCart cart) {
        // Check if the total quantity in the cart is within a limit (e.g., 10 items)
        int totalQuantity = cart.getItems().stream().mapToInt(ShoppingCartItem::getQuantity).sum();
        boolean isValid = totalQuantity <= 10;
        System.out.println("Validation result for the cart: " + (isValid ? "valid" : "invalid"));
        return isValid;
    }

}
