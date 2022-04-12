package webshop;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testCreate() {
        Order order = new Order(2, 1, 3, LocalDateTime.parse("2022-04-01T10:05"));
        assertEquals(2, order.getId());
        assertEquals(1, order.getProductId());
        assertEquals(3, order.getProductAmount());
        assertEquals(LocalDateTime.parse("2022-04-01T10:05"), order.getOrderDate());
    }
}
