package webshop;

import org.springframework.dao.EmptyResultDataAccessException;
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
                (rs, rowNum) -> new Product(rs.getLong("product_id"),rs.getString("product_name"),rs.getString("category"),rs.getLong("price"),rs.getLong("available_stock")));
    }

    public long getAvailableStockByProductId(long productId) {
        try {
            return template.queryForObject("select available_stock from products where product_id = ?", (rs, rowNum) -> rs.getLong("available_stock"), productId);
        }catch (EmptyResultDataAccessException | NullPointerException e){
            throw new IllegalArgumentException("Cannot find product!",e);
        }
    }

    public long getAvailableStockByProductName(String productName) {
        try {
            return template.queryForObject("select available_stock from products where product_name like ?", (rs, rowNum) -> rs.getLong("available_stock"), productName);
        }catch (EmptyResultDataAccessException | NullPointerException e){
            throw new IllegalArgumentException("Cannot find product!",e);
        }
    }

    public void updateStockOfProductById(long productId ,long amount){
        template.update("update products set available_stock=(available_stock+?)where product_id = ?",amount,productId);
    }

    public void updateStockOfProductByName(String productName ,long amount){
        template.update("update products set available_stock=(available_stock+?)where product_name like ?",amount,productName);
    }
}
