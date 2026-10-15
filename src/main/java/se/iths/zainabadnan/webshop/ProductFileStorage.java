package se.iths.zainabadnan.webshop;


import se.iths.zainabadnan.webshop.model.Product;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class ProductFileStorage implements ProductStorage {

    private Path file = Path.of("products.txt");

    public ProductFileStorage() {
    }

    public ProductFileStorage(Path file) {
        this.file = file;
    }

    @Override
    public void saveProduct(Product product) {
        String fileLine = product.toFileLine();
        try {
            Files.writeString(file, fileLine + System.lineSeparator(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new UncheckedIOException("Kunde inte spara produkten till filen " + file, e);
        }
    }


    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        if (!Files.exists(file)) {
            return products;
        }
        try (var lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isBlank())
                    .map(Product::fromFileLine)
                    .forEach(products::add);
        } catch (IOException e) {
            throw new UncheckedIOException("Kunde inte läsa produkterna från filen " + file, e);
        }
        return products;
    }


    @Override
    public Product getProduct(String articleNumber) {
        List<Product> products = getProducts();
        for (Product product : products) {
            if (product.getArticleNumber().equals(articleNumber)) {
                return product;
            }
        }
        return null;
    }
}