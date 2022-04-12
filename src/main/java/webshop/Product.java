package webshop;

public class Product {

    private final long id;
    private final String name;
    private final String category;
    private final long price;

    public Product(long id, String name, String category, long price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Product(long id) {
        this(id, null, null, 0);
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
}
