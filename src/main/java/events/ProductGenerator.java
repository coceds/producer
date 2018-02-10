package events;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductGenerator {

    private final List<Product> products;
    private final Random random = new Random();

    public ProductGenerator(int size) {
        int categoriesSize = size / 100 + 1;
        List<String> categories = IntStream.range(0, categoriesSize)
                .mapToObj(value -> UUID.randomUUID().toString())
                .collect(Collectors.toList());
        this.products = IntStream.range(0, size)
                .mapToObj(value -> new Product(
                        UUID.randomUUID().toString(),
                        categories.get(random.nextInt(categoriesSize)),
                        Math.round(Math.abs(random.nextGaussian() * 300 + 500))
                ))
                .collect(Collectors.toList());
    }

    public Product generate() {
        return products.get(random.nextInt(products.size()));
    }
}
