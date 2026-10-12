package se.iths.zainabadnan.webshop.model;

public class HalfPriceDiscount extends Discount {

    public HalfPriceDiscount() {
        super("50% rabatt");
    }

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.5;
    }
}
