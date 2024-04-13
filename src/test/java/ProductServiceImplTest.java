import org.example.model.Product;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ProductServiceImplTest {
    private ProductService productService;

    @Before
    public void setUp() {
        productService = new ProductServiceImpl(); // Assuming no dependencies
    }

    // Parameters for the test: original price, discount percentage, expected discounted price
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Product("1", "Product 1", 100.0, 10, 10.0), 10.0, 90.0},
                {new Product("2", "Product 2", 50.0, 5,10.0), 20.0, 40.0},
                {new Product("3", "Product 3", 75.0, 15,10.0), 25.0, 56.25},
                {new Product("4", "Product 4", 200.0, 20,10.0), 15.0, 170.0},
                {new Product("5", "Product 5", 80.0, 8,20.0), 5.0, 76.0}
        });
    }

    private Product product;
    private double discountPercentage;
    private double expectedDiscountedPrice;

    public ProductServiceImplTest(Product product, double discountPercentage, double expectedDiscountedPrice) {
        this.product = product;
        this.discountPercentage = discountPercentage;
        this.expectedDiscountedPrice = expectedDiscountedPrice;
    }

    @Test
    public void testCalculateDiscountedPrice() {
        // Act
        double actualDiscountedPrice = productService.calculateDiscountedPrice(product, discountPercentage);

        System.out.println("Product: " + product.getName());
        System.out.println("Original Price: " + product.getPrice());
        System.out.println("Discount Percentage: " + discountPercentage);
        System.out.println("Expected Discounted Price: " + expectedDiscountedPrice);
        System.out.println("Actual Discounted Price: " + actualDiscountedPrice);
        // Assert
        assertEquals(expectedDiscountedPrice, actualDiscountedPrice, 0.001);
    }

    @Test
    public void testPriceLessThan50_shouldNotBeEligibleForFreeShipping() {
        // Arrange
        Product product = new Product("49", "Headphones", 39.9, 1, 0.6);

        // No mocking needed here, method directly calls logic within ProductService

        // Act
        boolean isEligible = productService.isEligibleForFreeShipping(product);

        // Assert
        assertFalse(isEligible);
    }

    @Test
    public void testNoStock_shouldNotBeEligibleForFreeShipping() {
        // Arrange
        Product product = new Product("example_id", "Laptop", 999, 0, 6.0);

        // Act
        boolean isEligible = productService.isEligibleForFreeShipping(product);

        // Assert
        assertFalse(isEligible);
    }

    @Test
    public void testNameContainsFragile_shouldNotBeEligibleForFreeShipping() {
        // Arrange
        Product product = new Product("example_vase", "Fragile Vase", 100, 2, 9.0);

        // Act
        boolean isEligible = productService.isEligibleForFreeShipping(product);

        // Assert
        assertFalse(isEligible);
    }

    @Test
    public void testWeightOverLimit_shouldNotBeEligibleForFreeShipping() {
        // Arrange
        Product product = new Product("example_heavy", "Heavy Box", 29, 15,20.0);

        // Act
        boolean isEligible = productService.isEligibleForFreeShipping(product);

        // Assert
        assertFalse(isEligible);
    }

    @Test
    public void testAllCriteriaMet_shouldBeEligibleForFreeShipping() {
        // Arrange
        Product product = new Product("example_tshirt", "T-Shirt", 60, 2, 0.2);

        // Act
        boolean isEligible = productService.isEligibleForFreeShipping(product);

        // Assert
        assertTrue(isEligible);
    }
}
