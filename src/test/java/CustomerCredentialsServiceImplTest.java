import org.example.model.CustomerCredentials;
import org.example.model.Product;
import org.example.model.ShoppingCart;
import org.example.service.CustomerCredentialsServiceImpl;
import org.example.service.ShoppingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CustomerCredentialsServiceImplTest {

    @InjectMocks
    private CustomerCredentialsServiceImpl credentialsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConfirmCredentialsValid(){
        CustomerCredentials validCredentials = new CustomerCredentials("example@gmail.com",
                "aaabbbccc");
        var result = credentialsService.isCustomerValid(validCredentials);
        assertTrue(result);
    }

    @Test
    public void  shouldConfirmCredentialsNotValid(){
        CustomerCredentials invalidCredentials = new CustomerCredentials("example@yahoo.com",
                "aaabbbccc");
        var result = credentialsService.isCustomerValid(invalidCredentials);
        assertFalse(result);
    }

    public void shouldValidateEmailFormat(String email, boolean expected) {
        // Arrange
        CustomerCredentials credentials = new CustomerCredentials(email, "password123");

        // Act
        boolean result = credentialsService.isCustomerValid(credentials);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void shouldTestEncryptCredentials() {
        // Arrange
        CustomerCredentials customerCredentials = new CustomerCredentials("example@mail.com", "password123");

        // Act
        credentialsService.encryptCredentials(customerCredentials);

        // Assert
        // Verify that the encrypted password is not null or empty
        assertEquals(false, customerCredentials.getEncryptedPassword().isEmpty());
        // Verify that the original password is not the same as the encrypted password
        assertEquals(false, customerCredentials.getPassword().equals(customerCredentials.getEncryptedPassword()));

        // Verify that attempting to encrypt null credentials throws a NullPointerException
        NullPointerException exceptionForNullCredentials = assertThrows(NullPointerException.class, () -> {
            credentialsService.encryptCredentials(null);
        });
        // Check the exception message
        assertEquals("Invalid customer credentials", exceptionForNullCredentials.getMessage());

        // Arrange again for the next test
        CustomerCredentials customerCredentialsWithNullPassword = new CustomerCredentials("example@mail.com", null);

        // Verify that attempting to encrypt credentials with a null password throws a NullPointerException
        NullPointerException exceptionForNullPassword = assertThrows(NullPointerException.class, () -> {
            credentialsService.encryptCredentials(customerCredentialsWithNullPassword);
        });
        // Check the exception message
        assertEquals("Invalid customer credentials", exceptionForNullPassword.getMessage());
    }



}
