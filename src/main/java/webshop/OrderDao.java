package webshop;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public OrderDao(DataSource dataSource) {
    }

    public void addOrders(long userId, List<Order> orders) {
        // TODO
    }

    public List<Order> findOrdersByUserId(long userId) {
        // TODO
        return new ArrayList<>();
    }
}
