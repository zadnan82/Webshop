package se.iths.zainabadnan.webshop;

import se.iths.zainabadnan.webshop.model.Product;

import java.util.List;

public interface ProductStorage {

    void saveProduct(Product product);

    List<Product> getProducts();

    Product getProduct(String articleNumber);

}
