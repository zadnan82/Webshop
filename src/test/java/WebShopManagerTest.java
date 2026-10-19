import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.zainabadnan.webshop.ProductStorage;
import se.iths.zainabadnan.webshop.WebShopManager;
import se.iths.zainabadnan.webshop.model.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WebShopManagerTest {

    private ProductStorage storage;
    private WebShopManager webShopManager;

    @BeforeEach
    void setUp() {
        storage = new ProductListStorage();
        webShopManager = new WebShopManager(storage, new StubOutInputHandler());
    }

    @Test
    void savingTwoProductsGivesAListWithTwoProducts() {
        Product first = new Product("B001", "Clean Code", 299.0, "En bok om programmering");
        Product second = new Product("E001", "Hörlurar", 499.0, "Trådlösa hörlurar");

        webShopManager.saveProductToStorage(first);
        webShopManager.saveProductToStorage(second);

        List<Product> products = webShopManager.getProductsFromStorage();

        assertEquals(2, products.size());
    }

    @Test
    void savedProductCanBeFetchedByArticleNumber() {
        Product product = new Product("B001", "Clean Code", 299.0, "En bok om programmering");
        webShopManager.saveProductToStorage(product);

        Product fetched = webShopManager.getProductFromStorage("B001");

        assertEquals("B001", fetched.getArticleNumber());
    }


    @Test
    void unknownArticleNumberReturnsNull() {
        Product product = webShopManager.getProductFromStorage("FINNS-INTE");

        assertNull(product);
    }
}