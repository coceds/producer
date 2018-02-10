package events;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event {

    private static final String COMMA = ",";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final int id;
    private final String productName;
    private final double productPrice;
    private final LocalDate purchaseDate;
    private final String productCategory;
    private final String clientIp;

    public Event(int id,
                 Product product,
                 LocalDate purchaseDate,
                 String clientIp) {
        this.id = id;
        this.productName = product.getProductName();
        this.productPrice = product.getPrice();
        this.purchaseDate = purchaseDate;
        this.productCategory = product.getProductCategory();
        this.clientIp = clientIp;
    }

    public String toCsvString() {
        return new StringBuilder()
                .append(FORMATTER.format(this.purchaseDate)).append(COMMA)
                .append(Integer.toString(this.id)).append(COMMA)
                .append(this.productName).append(COMMA)
                .append(Double.toString(this.productPrice)).append(COMMA)
                .append(this.productCategory).append(COMMA)
                .append(this.clientIp)
                .toString();
    }
}
