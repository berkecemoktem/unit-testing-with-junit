import org.example.model.Product;
import org.example.service.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ProductServiceImplTest {
    private ProductServiceImpl productService;

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

    @Before
    public void setUp() {
        productService = new ProductServiceImpl();
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
}
