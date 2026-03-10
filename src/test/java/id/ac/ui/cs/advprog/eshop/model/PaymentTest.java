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

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        List<Product> products = new ArrayList<>();
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                    "MEOW", this.paymentData, this.order);
        });
    }

    @Test
    void testCreatePaymentCashOnDeliverySuccess() {
        this.paymentData.put("deliveryAddress", "Jalan PBP");
        this.paymentData.put("city", "Depok");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "CASH_ON_DELIVERY", this.paymentData, this.order);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedEmptyAddress() {
        this.paymentData.put("deliveryAddress", "");
        this.paymentData.put("city", "Depok");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "CASH_ON_DELIVERY", this.paymentData, this.order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejectedEmptyCity() {
        this.paymentData.put("deliveryAddress", "Jalan PBP");
        this.paymentData.put("city", "");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "CASH_ON_DELIVERY", this.paymentData, this.order);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        this.paymentData.put("deliveryAddress", "Jalan PBP");
        this.paymentData.put("city", "Depok");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "CASH_ON_DELIVERY", this.paymentData, this.order);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalidStatus() {
        this.paymentData.put("deliveryAddress", "Jalan PBP");
        this.paymentData.put("city", "Depok");
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                "CASH_ON_DELIVERY", this.paymentData, this.order);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}