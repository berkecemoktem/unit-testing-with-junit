import org.example.exception.shoppingcartservice.CheckoutFailedException;
import org.example.model.CustomerCredentials;
import org.example.model.ShoppingCart;
import org.example.service.CustomerCredentialsService;
import org.example.service.ShoppingService;
import org.example.service.ShoppingCartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingService shoppingService;

    @Mock
    private CustomerCredentialsService customerCredentialsService;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /*
    Test Case	ShoppingCart Validity	Customer Credentials Validity	Expected Outcome
    Case 1	    Valid	                Valid	                        Success
    Case 2	    Invalid	                Valid	                        Failure
    Case 3	    Valid	                Invalid	                        Failure
    Case 4	    Invalid	                Invalid	                        Failure
    */

    @Test
    public void shouldTestProcessCheckout_ValidCart_ValidCredentials() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials validCredentials = new CustomerCredentials("example@gmail.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(true);
        when(customerCredentialsService.isCustomerValid(validCredentials)).thenReturn(true);

        // Act
        boolean result = shoppingCartService.processCheckout(cart, validCredentials);

        // Assert
        assertTrue(result);
    }

    @Test(expected = CheckoutFailedException.class)
    public void shouldTestProcessCheckout_InvalidCart_ValidCredentials() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials validCredentials = new CustomerCredentials("example@gmail.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(false);
        // Note: We are not mocking the customer credentials validity check here

        // Act
        shoppingCartService.processCheckout(cart, validCredentials);
    }

    @Test(expected = CheckoutFailedException.class)
    public void shouldTestProcessCheckout_ValidCart_InvalidCredentials() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials invalidCredentials = new CustomerCredentials("example@yahoo.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(true);
        when(customerCredentialsService.isCustomerValid(invalidCredentials)).thenReturn(false);

        // Act
        shoppingCartService.processCheckout(cart, invalidCredentials);
    }

    @Test(expected = CheckoutFailedException.class)
    public void shouldTestProcessCheckout_InvalidCart_InvalidCredentials() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials invalidCredentials = new CustomerCredentials("example@yahoo.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(false);
        // Note: We are not mocking the customer credentials validity check here

        // Act
        shoppingCartService.processCheckout(cart, invalidCredentials);
    }


}
