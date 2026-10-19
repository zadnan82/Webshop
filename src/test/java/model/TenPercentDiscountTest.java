package model;

import org.junit.jupiter.api.Test;
import se.iths.zainabadnan.webshop.model.Discount;
import se.iths.zainabadnan.webshop.model.TenPercentDiscount;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TenPercentDiscountTest {

    @Test
    void calculatePriceGivesTenPercentDiscount() {
        Discount discount = new TenPercentDiscount();
        double price = 100.0;

        double discountedPrice = discount.calculatePrice(price);

        assertEquals(90.0, discountedPrice);
    }
}