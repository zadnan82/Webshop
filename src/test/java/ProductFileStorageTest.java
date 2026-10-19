import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import se.iths.zainabadnan.webshop.ProductFileStorage;
import se.iths.zainabadnan.webshop.ProductStorage;
import se.iths.zainabadnan.webshop.model.Product;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ProductFileStorageTest {

    @TempDir
    Path tempDir;

    private ProductStorage storage;

    @BeforeEach
    void setUp() {
        storage = new ProductFileStorage(tempDir.resolve("products.txt"));
    }

    @Test
    void getProductsReturnsEmptyListWhenFileDoesNotExist() {
        assertTrue(storage.getProducts().isEmpty());
    }

    @Test
    void savedProductCanBeReadBack() {
        storage.saveProduct(new Product("B001", "Clean Code", 299.0, "En bok om programmering"));

        List<Product> products = storage.getProducts();

        assertEquals(1, products.size());
        Product product = products.getFirst();
        assertEquals("B001", product.getArticleNumber());
        assertEquals("Clean Code", product.getTitle());
        assertEquals(299.0, product.getPrice());
        assertEquals("En bok om programmering", product.getDescription());
    }

    @Test
    void savingDoesNotClearTheFile() {
        storage.saveProduct(new Product("B001", "Clean Code", 299.0, "En bok"));
        storage.saveProduct(new Product("E001", "Hörlurar", 499.0, "Trådlösa hörlurar"));
        storage.saveProduct(new Product("C001", "T-shirt", 149.0, "Svart t-shirt"));

        assertEquals(3, storage.getProducts().size());
    }

    @Test
    void getProductFindsProductByArticleNumber() {
        storage.saveProduct(new Product("B001", "Clean Code", 299.0, "En bok"));
        storage.saveProduct(new Product("E001", "Hörlurar", 499.0, "Trådlösa hörlurar"));

        Product found = storage.getProduct("E001");

        assertNotNull(found);
        assertEquals("Hörlurar", found.getTitle());
    }

    @Test
    void getProductReturnsNullWhenArticleNumberIsUnknown() {
        storage.saveProduct(new Product("B001", "Clean Code", 299.0, "En bok"));

        assertNull(storage.getProduct("FINNS-INTE"));
    }

    @Test
    void productIsStillThereWhenStorageIsCreatedAgain() {
        storage.saveProduct(new Product("B001", "Clean Code", 299.0, "En bok"));

        ProductStorage reopened = new ProductFileStorage(tempDir.resolve("products.txt"));

        assertEquals(1, reopened.getProducts().size());
    }
}