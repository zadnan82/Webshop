package se.iths.zainabadnan.webshop;

import se.iths.zainabadnan.webshop.model.Discount;
import se.iths.zainabadnan.webshop.model.HalfPriceDiscount;
import se.iths.zainabadnan.webshop.model.Product;
import se.iths.zainabadnan.webshop.model.TenPercentDiscount;

import java.util.List;

public class WebShopManager {

    private ProductStorage productStorage;

    public WebShopManager(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }


    public void saveProductToStorage(Product product) {
        productStorage.saveProduct(product);
    }

    public List<Product> getProductsFromStorage() {
        return productStorage.getProducts();
    }


    public Product getProductFromStorage(String articleNumber) {
        return productStorage.getProduct(articleNumber);
    }


    public void startWebshop() {
        Discount discount = readDiscount();

        boolean running = true;
        while (running) {
            String choice = readMenuChoice();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> listProducts(discount);
                case "3" -> showProduct(discount);
                case "4" -> {
                    IO.println("Välkommen åter!");
                    running = false;
                }
                default -> IO.println("Ogiltigt val, ange 1, 2, 3 eller 4.");
            }
        }
    }


    private Discount readDiscount() {
        String code = IO.readln("Ange rabattkod (halfprice för halva priset): ");

        Discount discount;
        if ("halfprice".equals(code)) {
            discount = new HalfPriceDiscount();
        } else {
            discount = new TenPercentDiscount();
        }
        IO.println("Rabatt: " + discount.getDescription());
        return discount;
    }


    private String readMenuChoice() {
        IO.println();
        IO.println("1. Lägg till produkt");
        IO.println("2. Lista alla produkter");
        IO.println("3. Visa information om en produkt");
        IO.println("4. Avsluta");
        return IO.readln("Ditt val: ");
    }


    private void addProduct() {
        String articleNumber = IO.readln("Mata in artikelnummer: ");
        String title = IO.readln("Mata in titel: ");

        double price = 0.0;
        boolean priceOk = false;
        while (!priceOk) {
            String priceText = IO.readln("Mata in pris: ");
            try {
                price = Double.parseDouble(priceText);
                priceOk = true;
            } catch (NumberFormatException e) {
                IO.println("Priset måste vara en siffra, till exempel 299.0. Försök igen.");
            }
        }

        String description = IO.readln("Mata in beskrivning: ");

        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
        IO.println("Produkten har sparats.");
    }


    private void listProducts(Discount discount) {
        List<Product> products = getProductsFromStorage();

        if (products.isEmpty()) {
            IO.println("Det finns inga produkter att visa.");
            return;
        }

        IO.println("Antal produkter: " + products.size());
        for (Product product : products) {
            IO.println(product.getArticleNumber() + " | " + product.getTitle()
                    + " | pris: " + product.getPrice()
                    + " | rabatterat pris: " + discount.calculatePrice(product.getPrice()));
        }
    }


    private void showProduct(Discount discount) {
        String articleNumber = IO.readln("Mata in artikelnummer: ");
        Product product = getProductFromStorage(articleNumber);

        if (product == null) {
            IO.println("Hittade ingen produkt med artikelnummer " + articleNumber + ".");
            return;
        }
        IO.println(product);
        IO.println("Rabatterat pris: " + discount.calculatePrice(product.getPrice()));
    }
}
