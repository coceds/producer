package events;

public class Product {

    private final String productName;
    private final String productCategory;
    private final double price;

    public Product(String productName,
                   String productCategory,
                   double price) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public double getPrice() {
        return price;
    }
}
