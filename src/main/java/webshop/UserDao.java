package webshop;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDao {

    private JdbcTemplate template;

    public UserDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    public void registerUser(String username, String password, String email, String address) {
        template.update("insert into users(username,password,email,address) values(?,?,?,?)",username,password,email,address);
    }

    public long logIn(String username, String password) {
        try {
            return template.queryForObject("select user_id from users where username like ? and password like ?", (rs, rowNum) -> rs.getLong("user_id"), username, password);
        }catch (EmptyResultDataAccessException | NullPointerException e){
            throw new IllegalArgumentException("Invalid credentials",e);
        }
    }
}
