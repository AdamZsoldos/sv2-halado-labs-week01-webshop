package webshop;

import javax.sql.DataSource;

public class UserDao {

    public UserDao(DataSource dataSource) {
    }

    public void registerUser(String username, String password, String email, String address) {
        // TODO
    }

    public long logIn(String username, String password) {
        // TODO
        return 0;
    }
}
