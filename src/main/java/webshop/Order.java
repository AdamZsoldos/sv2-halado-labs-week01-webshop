package webshop;

import java.time.LocalDateTime;

public class Order {

    private final long id;
    private final Product product;
    private final long amount;
    private final LocalDateTime orderDate;

    public Order(long id, Product product, long amount, LocalDateTime orderDate) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    public Order(Product product, long amount, LocalDateTime orderDate) {
        this(0, product, amount, orderDate);
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
