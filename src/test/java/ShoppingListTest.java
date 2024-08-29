import edu.eci.arep.services.AddProductService;
import edu.eci.arep.services.GetProductsService;
import edu.eci.arep.utils.ShoppingListMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingListTest {

    private ShoppingListMemory shoppingListMemory;

    @BeforeEach
    void setUp() {
        shoppingListMemory = new ShoppingListMemory();
    }

    @Test
    void testAddProduct() {
        // Arrange
        AddProductService addProductService = new AddProductService(shoppingListMemory);
        String product = "Test Product";
        String request = "name=" + product;

        // Act
        String response = addProductService.response(request);

        // Assert
        assertEquals("{\"message\":\"Product added successfully.\"}", response);
        assertEquals(1, shoppingListMemory.getProducts().size());
        assertEquals(product, shoppingListMemory.getProducts().get(0));
    }

    @Test
    void testGetProducts() {
        // Arrange
        shoppingListMemory.addProduct("Product 1");
        shoppingListMemory.addProduct("Product 2");
        GetProductsService getProductsService = new GetProductsService(shoppingListMemory);

        // Act
        String response = getProductsService.response("");

        // Assert
        assertEquals("{\"products\":[\"Product 1\",\"Product 2\"]}", response);
    }

    @Test
    void testProductExistsAfterAdding() {
        // Arrange
        shoppingListMemory.addProduct("New Product");
        GetProductsService getProductsService = new GetProductsService(shoppingListMemory);

        // Act
        String response = getProductsService.response("");

        // Assert
        assertEquals("{\"products\":[\"New Product\"]}", response);
    }

    @Test
    void testProductAlreadyExists() {
        // Arrange
        AddProductService addProductService = new AddProductService(shoppingListMemory);
        shoppingListMemory.addProduct("Existing Product");
        String request = "name=Existing%20Product";

        // Act
        String response = addProductService.response(request);

        // Assert
        assertEquals("{\"message\":\"Product already exists in the cart.\"}", response);
    }
}
