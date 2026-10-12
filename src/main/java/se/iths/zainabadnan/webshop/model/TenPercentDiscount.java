package se.iths.zainabadnan.webshop.model;

public class TenPercentDiscount extends Discount {

    public TenPercentDiscount() {
        super("10% rabatt");
    }

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.9;
    }
}
