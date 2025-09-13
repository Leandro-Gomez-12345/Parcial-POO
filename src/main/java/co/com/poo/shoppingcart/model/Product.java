package co.com.poo.shoppingcart.model;

public class Product {

    private final Integer id;
    private final String name;
    private final String category;
    private final Double price;

    public Product(Integer id, String name, String category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
