import se.iths.zainabadnan.webshop.ProductStorage;
import se.iths.zainabadnan.webshop.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductListStorage implements ProductStorage {

    private List<Product> products = new ArrayList<>();

    @Override
    public void saveProduct(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product getProduct(String articleNumber) {
        for (Product product : products) {
            if (product.getArticleNumber().equals(articleNumber)) {
                return product;
            }
        }
        return null;
    }
}
