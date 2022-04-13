package webshop;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ProductDao {

    JdbcTemplate template;

    public ProductDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void addProducts(List<Product> products) {
        for (Product p:products) {
        template.update("insert into products(product_name,category,price) values(?,?,?)",p.getName(),p.getCategory(),p.getPrice());
        }
    }

    public List<Product> findAllProducts() {
        return template.query("select * from products",
                (rs, rowNum) -> new Product(rs.getLong("product_id"),rs.getString("product_name"),rs.getString("category"),rs.getLong("price")));
    }
}
