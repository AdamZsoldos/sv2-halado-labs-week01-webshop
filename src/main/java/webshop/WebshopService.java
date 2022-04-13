package webshop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

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
        UserValidator.validateUser(username, password, email, address);
        userDao.registerUser(username, password, email, address);
    }

    public void logIn(String username, String password) {
        currentUserId = userDao.logIn(username, password);
        loggedIn = true;
    }

    public void addProductToCart(long productId, long amount) {
        validateLoggedIn();
        long totalAmount = (cart.containsKey(productId) ? cart.get(productId) : 0) + amount;
        validateStock(productId, totalAmount);
        cart.put(productId, totalAmount);
    }

    public void removeProductFromCart(long productId) {
        validateLoggedIn();
        cart.remove(productId);
    }

    public void placeOrder() {
        validateLoggedIn();
        validateCartNotEmpty();
        cart.forEach(this::validateStock);
        cart.forEach((k, v) -> productDao.updateStockOfProductById(k, -v));
        orderDao.addOrders(currentUserId, createOrdersFromCart());
        cart.clear();
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
        try {
            List<String> lines = Files.readAllLines(path);
            List<Product> products = lines.stream().map(this::parseLine).toList();
            productDao.addProductsWithStock(products);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read file", e);
        }
    }

    private Product parseLine(String line) {
        String[] fields = line.split(";");
        String name = fields[0];
        String category = fields[1];
        long price = Long.parseLong(fields[2]);
        long stock = Long.parseLong(fields[3]);
        return new Product(name, category, price, stock);
    }

    private void validateLoggedIn() {
        if (!loggedIn) {
            throw new IllegalStateException("Not logged in");
        }
    }

    private void validateCartNotEmpty() {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
    }

    private void validateStock(long productId, long amount) {
        if (productDao.getAvailableStockByProductId(productId) < amount) {
            throw new IllegalStateException("Insufficient stock for product ID " + productId);
        }
    }
}
