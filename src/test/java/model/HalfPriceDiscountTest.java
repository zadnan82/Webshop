package model;

import org.junit.jupiter.api.Test;
import se.iths.zainabadnan.webshop.model.Discount;
import se.iths.zainabadnan.webshop.model.HalfPriceDiscount;

import static org.junit.jupiter.api.Assertions.assertEquals;
 
class HalfPriceDiscountTest {

    @Test
    void calculatePriceGivesHalfPrice() {
        Discount discount = new HalfPriceDiscount();
        double price = 100.0;

        double discountedPrice = discount.calculatePrice(price);

        assertEquals(50.0, discountedPrice);
    }
}