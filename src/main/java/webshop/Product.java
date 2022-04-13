package webshop;

public class Product {

    private final long id;
    private final String name;
    private final String category;
    private final long price;
    private final long stock;

    public Product(long id, String name, String category, long price, long stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, String category, long price, long stock) {
        this(0, name, category, price, stock);
    }

    public Product(String name, String category, long price) {
        this(0, name, category, price, 0);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public long getStock() {
        return stock;
    }
}
