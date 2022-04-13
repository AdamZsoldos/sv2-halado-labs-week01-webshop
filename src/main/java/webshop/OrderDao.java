package webshop;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDao {

    private JdbcTemplate template;

    public OrderDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void addOrders(long userId, List<Order> orders) {
        for (Order o :orders) {
        template.update("insert into orders(user_id,product_id,product_amount,order_date) values(?,?,?,?)",userId,o.getProductId(),o.getProductAmount(),o.getOrderDate());
        }
    }

    public List<Order> findOrdersByUserId(long userId) {
        return template.query("select * from orders where user_id = ?",(rs, rowNum) -> new Order(rs.getLong("order_id"),rs.getLong("product_id"),rs.getLong("product_amount"), rs.getObject("order_date",LocalDateTime.class)),userId);
    }
}
