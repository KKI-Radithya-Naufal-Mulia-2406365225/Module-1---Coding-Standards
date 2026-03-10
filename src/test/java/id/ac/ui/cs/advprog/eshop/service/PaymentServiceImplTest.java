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
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("deliveryAddress", "Depok");
        data.put("city", "Depok");
        doAnswer(inv -> inv.getArgument(0)).when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.createPayment(order, "CASH_ON_DELIVERY", data);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testUpdateStatusSuccess() {
        Payment payment = new Payment("id", "CASH_ON_DELIVERY", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).findById("id");
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        Payment result = paymentService.updateStatus("id", "SUCCESS");
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testUpdateStatusNotFound() {
        doReturn(null).when(paymentRepository).findById("invalid");
        assertThrows(NoSuchElementException.class, () -> paymentService.updateStatus("invalid", "SUCCESS"));
    }
}