import org.example.service.ShoppingCartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.example.exception.shoppingcartservice.CheckoutFailedException;
import org.example.model.CustomerCredentials;
import org.example.model.ShoppingCart;
import org.example.service.CustomerCredentialsService;
import org.example.service.ShoppingService;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

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

    @Test
    public void shouldTestProcessCheckoutWithInvalidCart() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials validCredentials = new CustomerCredentials("example@gmail.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(true);
        when(customerCredentialsService.isCustomerValid(validCredentials)).thenReturn(true);
        when(shoppingService.calculateTotal(cart)).thenReturn(100.0);

        // Act
        boolean result = shoppingCartService.processCheckout(cart, validCredentials);

        // Assert
        assertTrue(result);
        // Verify that the necessary methods were called
        verify(shoppingService).validateCart(cart);
        verify(customerCredentialsService).isCustomerValid(validCredentials);
        verify(shoppingService).calculateTotal(cart);
    }

    @Test(expected = CheckoutFailedException.class)
    public void shouldTestProcessCheckoutWithInvalidCredentials() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials validCredentials = new CustomerCredentials("example@gmail.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(false);

        // Act
        shoppingCartService.processCheckout(cart, validCredentials);
    }

    @Test(expected = CheckoutFailedException.class)
    public void shouldTestProcessCheckoutSuccess() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        CustomerCredentials invalidCredentials = new CustomerCredentials("example@yahoo.com", "password123");

        // Mock behavior for the dependencies
        when(shoppingService.validateCart(cart)).thenReturn(true);
        when(customerCredentialsService.isCustomerValid(invalidCredentials)).thenReturn(false);

        // Act
        shoppingCartService.processCheckout(cart, invalidCredentials);
    }
}
