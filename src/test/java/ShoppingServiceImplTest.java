import org.example.model.Product;
import org.example.model.ShoppingCart;
import org.example.model.ShoppingCartItem;
import org.example.service.ShoppingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.Mockito.when;

public class ShoppingServiceImplTest {

    @Mock
    private ShoppingCart cart;

    @InjectMocks
    private ShoppingServiceImpl shoppingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldTestAddToCart() {
        Product smartphone = new Product("P002", "Smartphone", 499.99, 10,1.0);

        when(cart.getItems()).thenReturn(new ArrayList<>());

        shoppingService.addToCart(cart, smartphone, 2);

        assertEquals(1, cart.getItems().size());
        assertEquals(8, smartphone.getStock());
    }

   @Test
    public void shouldTestAddToCartWhenProductExists() {
        ShoppingCart cart = new ShoppingCart();
        Product laptop = new Product("L001", "Laptop", 999.99, 5,4.0);
        ShoppingCartItem existingItem = new ShoppingCartItem(laptop, 2, true);
        cart.setItems(Collections.singletonList(existingItem));

        shoppingService.addToCart(cart, laptop, 3);

        assertEquals(5, existingItem.getQuantity());
        assertEquals(1, cart.getItems().size());
    }


    @Test
    public void shouldTestAddToCartWhenQuantityInvalid() {
        ShoppingCart cart = new ShoppingCart();
        Product tablet = new Product("T003", "Tablet", 299.99, 3,1.5);

        // Use assertThrows to check if the addToCart method throws IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingService.addToCart(cart, tablet, -1);
        });

        // Check the exception message
        assertEquals("Invalid quantity or insufficient stock.", exception.getMessage());
    }


    //Original test method before the mutated version.
    @Test
    public void shouldTestRemoveFromCart() {
        ShoppingCart cart = new ShoppingCart();
        Product laptop = new Product("L001", "Laptop", 999.99, 5,3.0);
        Product smartphone = new Product("P002", "Smartphone", 499.99, 10,1.0);

        shoppingService.addToCart(cart, laptop, 3);
        shoppingService.addToCart(cart, smartphone, 2);

        // Remove 2 laptops from the cart
        shoppingService.removeFromCart(cart, laptop, 2);

        // Verify the updated cart state
        assertEquals(1, cart.getItems().get(0).getQuantity());

        // Verify the updated product stock
        assertEquals(4, laptop.getStock());
    }


    @Test
    public void shouldTestRemoveFromCartWhenQuantityExceeds() {
        ShoppingCart cart = new ShoppingCart();
        Product headphones = new Product("H001", "HeadphoneXQQ", 99.99, 11,0.5);
        shoppingService.addToCart(cart, headphones, 2);

        // Remove 3 headphones from the cart -> which is invalid since 3 > 2
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            shoppingService.removeFromCart(cart, headphones, 3);
        });

        // Check the exception message
        assertEquals("Requested quantity exceeds the quantity of the product in the cart.", exception.getMessage());
    }

    @Test
    public void shouldTestRemoveFromCartWhenProductNotFound() {
        ShoppingCart cart = new ShoppingCart();

        // Add some products to the cart
        Product exampleProduct1 = new Product("1", "example1", 20.0, 2,6.0);
        Product exampleProduct2 = new Product("2", "example2", 15.0, 3,12.0);

        ShoppingCartItem item1 = new ShoppingCartItem(exampleProduct1, 2, true);
        ShoppingCartItem item2 = new ShoppingCartItem(exampleProduct2, 3, true);

        cart.setItems(Arrays.asList(item1, item2));

        // Create a product not present in the cart
        Product tshirt = new Product("T001", "DesignerX", 19.99, 200,5.0);

        // Use assertThrows to check if the removeFromCart method throws an exception when the product is not found
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            shoppingService.removeFromCart(cart, tshirt, 1);
        });

        // Check the exception message
        assertEquals("Product not found in the cart.", exception.getMessage());
    }

    /*******Test cases for mutation codes*******/

    // Mutation 1: Negating a condition
    // Mutation 2: Changing a constant value
    @Test
    public void testRemoveFromCartMutated_Mutation1And2() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        Product exampleProduct = new Product("1", "example1", 20.0, 5,14.0);
        ShoppingCartItem item = new ShoppingCartItem(exampleProduct, 5, true);
        cart.setItems(Collections.singletonList(item));

        // Act & Assert
        // The mutated method should throw an exception since the product is present in the cart,
        // and the requested quantity exceeds the existing quantity
        assertThrows(IllegalArgumentException.class, () -> shoppingService.removeFromCartMutated(cart, exampleProduct, 7));
    }

    //Mutation 3:
    @Test
    public void testRemoveFromCartMutated_Mutation3() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        Product exampleProduct = new Product("1", "example1", 20.0, 5,11.0);
        ShoppingCartItem item = new ShoppingCartItem(exampleProduct, 5, true);
        cart.setItems(Collections.singletonList(item));

        // Act
        shoppingService.removeFromCartMutated(cart, exampleProduct, 3);

        // Assert
        // Validate that the stock of the product is not updated after removing items
        assertEquals(2, exampleProduct.getStock());
    }

    // Mutation 4: Changing the method call parameter
    @Test
    public void testRemoveFromCartMutated_Mutation4() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        Product exampleProduct = new Product("1", "example1", 20.0, 10,10.0);
        ShoppingCartItem item = new ShoppingCartItem(exampleProduct, 10, true);
        cart.setItems(Collections.singletonList(item));

        // Act
        shoppingService.removeFromCartMutated(cart, exampleProduct, 3);

        // Assert
        // The mutated method should update the quantity by adding the requested quantity
        assertEquals(13, item.getQuantity());
    }


    /********End of the mutation testing********/

    @Test
    public void shouldTestGetCartItemByNameWhenProductNotInCart() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        String productName = "NonexistentProduct";

        // Act
        ShoppingCartItem cartItem = shoppingService.getCartItemByName(cart, productName);

        // Assert
        assertNull(cartItem);
    }

    @Test
    public void shouldTestCalculateTotalForItemsInCart() {
        ShoppingCart cart = new ShoppingCart();
        Product laptop = new Product("L001", "Laptop", 999.99, 5,0.7);
        Product smartphone = new Product("P002", "Smartphone", 499.99, 10,0.4);

        shoppingService.addToCart(cart, laptop, 2);
        shoppingService.addToCart(cart, smartphone, 3);

        double total = shoppingService.calculateTotal(cart);

        assertEquals(2 * laptop.getPrice() + 3 * smartphone.getPrice(), total, 0.01);
    }
    @Test
    public void shouldTestCalculateTotalForEmptyCart() {
        ShoppingCart cart = new ShoppingCart();

        double total = shoppingService.calculateTotal(cart);

        assertEquals(0.0, total, 0.01);
    }
    @Test
    public void shouldTestValidateValidCart() {
        ShoppingCart cart = new ShoppingCart();
        Product laptop = new Product("L001", "Laptop", 999.99, 5,2.0);
        shoppingService.addToCart(cart, laptop, 3);

        boolean isValid = shoppingService.validateCart(cart);

        assertEquals(true, isValid);
    }
    @Test
    public void shouldTestValidateInvalidCart() {
        ShoppingCart cart = new ShoppingCart();
        Product laptop = new Product("L001", "Laptop", 999.99, 5,1.5);
        Product smartphone = new Product("P002", "Smartphone", 499.99, 10,2.0);
        shoppingService.addToCart(cart, laptop, 3);
        shoppingService.addToCart(cart, smartphone, 8);

        boolean isValid = shoppingService.validateCart(cart);

        assertEquals(false, isValid);
    }

    @Test
    public void testCalculateTotalWithTimeout() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        ShoppingServiceImpl shoppingService = new ShoppingServiceImpl();

        // Add items to the cart
        Product laptop = new Product("L001", "Laptop", 999.99, 50,0.5);
        Product smartphone = new Product("P002", "Smartphone", 499.99, 10,0.4);
        shoppingService.addToCart(cart, laptop, 2);
        shoppingService.addToCart(cart, smartphone, 3);

        // Act and Assert
        assertTimeout(Duration.ofSeconds(5), () -> {
            double total = shoppingService.calculateTotal(cart);
            // You can add additional assertions for the total if needed
        });
    }
}