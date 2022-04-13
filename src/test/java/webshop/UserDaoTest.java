package webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {
    Flyway flyway;
    UserDao userDao;
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

        userDao = new UserDao(dataSource);
    }

    @Test
    void testRegisterAndLogin(){
        userDao.registerUser("jani","asdf","jani@jani.com","Bp. Ferenc utca 3.");
        long id=userDao.logIn("jani","asdf");
        assertEquals(1,id);
    }
}
