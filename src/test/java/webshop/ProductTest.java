package webshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testCreate() {
        Product product = new Product(1, "Milk", "Groceries", 300);
        assertEquals(1, product.getId());
        assertEquals("Milk", product.getName());
        assertEquals("Groceries", product.getCategory());
        assertEquals(300, product.getPrice());
    }
}
