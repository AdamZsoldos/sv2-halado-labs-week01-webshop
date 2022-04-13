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
    }

    @Test
    void testAddProductsAndListProducts(){
        productDao.addProducts(List.of(new Product("ego","haztartas",500),
                new Product("korte","elelmiszer",400),
                new Product("alma","elelmiszer",200)));
        List<Product> results = productDao.findAllProducts();
        assertEquals(3,results.size());
    }
}
