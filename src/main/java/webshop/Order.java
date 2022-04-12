package webshop;

import java.time.LocalDateTime;

public class Order {

    private final long id;
    private final long productId;
    private final long productAmount;
    private final LocalDateTime orderDate;

    public Order(long id, long productId, long productAmount, LocalDateTime orderDate) {
        this.id = id;
        this.productId = productId;
        this.productAmount = productAmount;
        this.orderDate = orderDate;
    }

    public Order(long productId, long productAmount, LocalDateTime orderDate) {
        this(0, productId, productAmount, orderDate);
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public long getAmount() {
        return amount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
