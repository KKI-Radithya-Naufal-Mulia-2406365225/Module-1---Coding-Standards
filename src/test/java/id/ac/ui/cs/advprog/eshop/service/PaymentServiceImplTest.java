package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");

        this.payments = new ArrayList<>();
    }

    @Test
    void testCreatePayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryAddress", "Jalan PBP");
        paymentData.put("city", "Depok");

        Payment payment = new Payment(UUID.randomUUID().toString(),
                "CASH_ON_DELIVERY", paymentData, order);

        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.createPayment(order, "CASH_ON_DELIVERY", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testUpdateStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryAddress", "Jalan PBP");
        paymentData.put("city", "Depok");

        Payment payment = new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                "CASH_ON_DELIVERY", paymentData, order);

        doReturn(payment).when(paymentRepository).findById(payment.getId());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.updateStatus(payment.getId(), "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdateStatusInvalidId() {
        doReturn(null).when(paymentRepository).findById("zczc");

        assertThrows(NoSuchElementException.class, () ->
                paymentService.updateStatus("zczc", "SUCCESS"));
    }
}