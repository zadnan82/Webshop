package se.iths.zainabadnan.webshop;

import se.iths.zainabadnan.webshop.model.Discount;
import se.iths.zainabadnan.webshop.model.HalfPriceDiscount;
import se.iths.zainabadnan.webshop.model.Product;
import se.iths.zainabadnan.webshop.model.TenPercentDiscount;

import java.util.List;

public class WebShopManager {

    private ProductStorage productStorage;
    private OutInputHandler outInputHandler;

    public WebShopManager(ProductStorage productStorage, OutInputHandler outInputHandler) {
        this.productStorage = productStorage;
        this.outInputHandler = outInputHandler;
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
            String choice = outInputHandler.menu();

            switch (choice) {
                case "1" -> addProduct();
                case "2" -> listProducts(discount);
                case "3" -> showProduct(discount);
                case "4" -> {
                    outInputHandler.info("Välkommen åter!");
                    running = false;
                }
                default -> outInputHandler.info("Ogiltigt val, ange 1, 2, 3 eller 4.");
            }
        }
    }


    private Discount readDiscount() {
        String code = outInputHandler.prompt("Ange rabattkod (halfprice för halva priset): ");

        Discount discount;
        if ("halfprice".equals(code)) {
            discount = new HalfPriceDiscount();
        } else {
            discount = new TenPercentDiscount();
        }
        outInputHandler.info("Rabatt: " + discount.getDescription());
        return discount;
    }


    private void addProduct() {
        String articleNumber = outInputHandler.prompt("Mata in artikelnummer: ");
        String title = outInputHandler.prompt("Mata in titel: ");
        double price = readPrice();
        String description = outInputHandler.prompt("Mata in beskrivning: ");

        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
        outInputHandler.info("Produkten har sparats.");
    }

    private double readPrice() {
        while (true) {
            String priceText = outInputHandler.prompt("Mata in pris: ");
            try {
                return Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                outInputHandler.info("Priset måste vara en siffra, till exempel 299.0. Försök igen.");
            }
        }
    }


    private void listProducts(Discount discount) {
        List<Product> products = getProductsFromStorage();

        if (products.isEmpty()) {
            outInputHandler.info("Det finns inga produkter att visa.");
            return;
        }

        outInputHandler.info("Antal produkter: " + products.size());
        for (Product product : products) {
            outInputHandler.info(product.getArticleNumber() + " | " + product.getTitle()
                    + " | pris: " + product.getPrice()
                    + " | rabatterat pris: " + discount.calculatePrice(product.getPrice()));
        }
    }


    private void showProduct(Discount discount) {
        String articleNumber = outInputHandler.prompt("Mata in artikelnummer: ");
        Product product = getProductFromStorage(articleNumber);

        if (product == null) {
            outInputHandler.info("Hittade ingen produkt med artikelnummer " + articleNumber + ".");
            return;
        }
        outInputHandler.info(product.toString());
        outInputHandler.info("Rabatterat pris: " + discount.calculatePrice(product.getPrice()));
    }
}