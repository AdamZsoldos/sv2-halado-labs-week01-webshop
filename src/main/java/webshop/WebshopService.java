package webshop;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WebshopService {

    private boolean loggedIn;
    private long currentUserId;
    private final Map<Long, Long> cart = new LinkedHashMap<>();

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final ProductDao productDao;

    public WebshopService(UserDao userDao, OrderDao orderDao, ProductDao productDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public long getCurrentUserId() {
        return currentUserId;
    }

    public Map<Long, Long> getCart() {
        return cart;
    }

    public void registerUser(String username, String password, String email, String address) {
        userDao.registerUser(username, password, email, address);
    }

    public void logIn(String username, String password) {
        currentUserId = userDao.logIn(username, password);
        loggedIn = true;
    }

    public void addProductToCart(long productId, long amount) {
        validateLoggedIn();
        cart.compute(productId, (k, v) -> v == null ? amount : v + amount);
    }

    public void removeProductFromCart(long productId) {
        validateLoggedIn();
        cart.remove(productId);
    }

    public void placeOrder() {
        validateLoggedIn();
        orderDao.addOrders(currentUserId, createOrdersFromCart());
    }

    private List<Order> createOrdersFromCart() {
        List<Order> orders = new ArrayList<>();
        LocalDateTime orderDate = LocalDateTime.now();
        for (Map.Entry<Long, Long> item : cart.entrySet()) {
            orders.add(new Order(
                    item.getKey(),
                    item.getValue(),
                    orderDate
            ));
        }
        return orders;
    }

    public List<Order> listAllOrders() {
        validateLoggedIn();
        return orderDao.findOrdersByUserId(currentUserId);
    }

    public List<Product> listAllProducts() {
        return productDao.findAllProducts();
    }

    public void loadProductsFromFile(Path path) {
        // TODO
    }

    private void validateLoggedIn() {
        if (!loggedIn) {
            throw new IllegalStateException("Must be logged in");
        }
    }
}
