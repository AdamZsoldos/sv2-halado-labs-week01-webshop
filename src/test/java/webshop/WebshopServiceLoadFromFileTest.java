package webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebshopServiceLoadFromFileTest {

    WebshopService webshopService;

    @BeforeEach
    void setUp() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/webshop_test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("root");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect", e);
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        ProductDao productDao = new ProductDao(dataSource);
        webshopService = new WebshopService(null, null, productDao);
    }

    @Test
    void testLoadProductsFromFile() {
        webshopService.loadProductsFromFile(Path.of("src/test/resources/products.csv"));
        List<Product> products = webshopService.listAllProducts();
        assertEquals(4, products.size());
        assertEquals("Milk", products.get(0).getName());
        assertEquals("Clothing", products.get(2).getCategory());
        assertEquals(400, products.get(3).getPrice());
    }

    @Test
    void testLoadProductsFromFileInvalidPath() {
        Exception e = assertThrows(IllegalStateException.class, () -> webshopService.loadProductsFromFile(Path.of("src/test/resources/products.csv_")));
        assertEquals("Cannot read file", e.getMessage());
        assertEquals(0, webshopService.listAllProducts().size());
    }
}
