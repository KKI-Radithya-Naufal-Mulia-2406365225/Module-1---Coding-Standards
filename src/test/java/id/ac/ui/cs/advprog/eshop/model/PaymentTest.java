package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        products.add(product);
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("id", "MEOW", paymentData, order));
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        paymentData.put("voucherCode", "PBP12345678abcde");
        Payment payment = new Payment("id", "VOUCHER", paymentData, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliverySuccess() {
        paymentData.put("deliveryAddress", "Jalan PBP");
        paymentData.put("city", "Depok");
        Payment payment = new Payment("id", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejected() {
        paymentData.put("deliveryAddress", "");
        Payment payment = new Payment("id", "CASH_ON_DELIVERY", paymentData, order);
        assertEquals("REJECTED", payment.getStatus());
    }
}