package webshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebshopServiceNotLoggedInTest {

    WebshopService webshopService = new WebshopService(null, null, null);

    @Test
    void testNotLoggedIn() {
        assertFalse(webshopService.isLoggedIn());
    }

    @Test
    void testAddProductToCartWhileNotLoggedIn() {
        Exception e = assertThrows(IllegalStateException.class, () -> webshopService.addProductToCart(1, 10));
        assertEquals("Not logged in", e.getMessage());
    }

    @Test
    void testRemoveProductFromCartWhileNotLoggedIn() {
        Exception e = assertThrows(IllegalStateException.class, () -> webshopService.removeProductFromCart(1));
        assertEquals("Not logged in", e.getMessage());
    }

    @Test
    void testPlaceOrderWhileNotLoggedIn() {
        Exception e = assertThrows(IllegalStateException.class, () -> webshopService.placeOrder());
        assertEquals("Not logged in", e.getMessage());
    }

    @Test
    void testListAllOrdersWhileNotLoggedIn() {
        Exception e = assertThrows(IllegalStateException.class, () -> webshopService.listAllOrders());
        assertEquals("Not logged in", e.getMessage());
    }
}
