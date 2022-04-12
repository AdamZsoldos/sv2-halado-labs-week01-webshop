package webshop;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testCreate() {
        Order order = new Order(2, new Product(1, "Milk", "Groceries", 300), 3, LocalDateTime.parse("2022-04-01T10:05"));
        assertEquals(2, order.getId());
        assertEquals(1, order.getProduct().getId());
        assertEquals("Milk", order.getProduct().getName());
        assertEquals("Groceries", order.getProduct().getCategory());
        assertEquals(300, order.getProduct().getPrice());
        assertEquals(3, order.getAmount());
        assertEquals(LocalDateTime.parse("2022-04-01T10:05"), order.getOrderDate());
    }
}
