import lombok.NoArgsConstructor;
import org.example.model.CustomerCredentials;
import org.example.service.CustomerCredentialsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;


public class CustomerCredentialsServiceImplTest {
    private String email;
    private boolean expectedValidation;

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

    @Parameterized.Parameters
    public static Collection<Object[]> primeNumbers() {
        return Arrays.asList(new Object[][]{
                {"xx@gmail.com", true},
                {"example@yahoo.com", false},
                {"test@hotmail.com", true}
        });
    }

    @Test
    public void shouldValidateEmailFormat() {
        // Arrange
        CustomerCredentials credentials = new CustomerCredentials(email, "password123");

        // Act
        boolean result = credentialsService.isCustomerValid(credentials);

        // Assert
        assertEquals(expectedValidation, result);
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
