package webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductDaoTest {
    Flyway flyway;
    ProductDao productDao;
    @BeforeEach
    void init(){
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try{
            dataSource.setUrl("jdbc:mariadb://localhost:3306/webshop_test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("root");
        }catch (SQLException e){
            throw new IllegalStateException("Cannot reach DataBase!",e);
        }
        flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        productDao = new ProductDao(dataSource);
        productDao.addProducts(List.of(new Product("ego","haztartas",500,0),
                new Product("korte","elelmiszer",400,0),
                new Product("alma","elelmiszer",200,0)));
    }

    @Test
    void testAddProductsAndListProducts(){
        productDao.addProductsWithStock(List.of(new Product("sajt","elelmiszer",200,40)));
        List<Product> results = productDao.findAllProducts();
        assertEquals(4,results.size());
        assertEquals(40,productDao.getAvailableStockByProductId(4));
    }

    @Test
    void testUpdateStockById(){
        productDao.updateStockOfProductById(1,30);
        productDao.updateStockOfProductById(1,-20);
        assertEquals(10,productDao.getAvailableStockByProductId(1));
    }

    @Test
    void testUpdateStockByName(){
        productDao.updateStockOfProductByName("alma",30);
        productDao.updateStockOfProductByName("korte",20);
        assertEquals(30,productDao.getAvailableStockByProductName("alma"));
        assertEquals(20,productDao.getAvailableStockByProductName("korte"));
    }
}
