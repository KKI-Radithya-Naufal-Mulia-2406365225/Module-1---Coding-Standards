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
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentVoucherSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "PBP12345678abcde");

        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.createPayment(order, "VOUCHER", paymentData);

        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testCreatePaymentVoucherRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "NOT-PBP-CODE");

        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.createPayment(order, "VOUCHER", paymentData);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliverySuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryAddress", "Fasilkom UI");
        paymentData.put("city", "Depok");

        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.createPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testCreatePaymentCashOnDeliveryRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("deliveryAddress", "");

        doAnswer(invocation -> invocation.getArgument(0)).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.createPayment(order, "CASH_ON_DELIVERY", paymentData);

        assertEquals("REJECTED", result.getStatus());
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
    void testUpdateStatusInvalidId() {
        doReturn(null).when(paymentRepository).findById("invalid");

        assertThrows(NoSuchElementException.class, () ->
                paymentService.updateStatus("invalid", "SUCCESS"));
    }

    @Test
    void testFindById() {
        Payment payment = new Payment("id", "CASH_ON_DELIVERY", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).findById("id");

        Payment result = paymentService.findById("id");
        assertEquals("id", result.getId());
    }

    @Test
    void testFindAll() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("id1", "VOUCHER", new HashMap<>(), order));
        payments.add(new Payment("id2", "CASH_ON_DELIVERY", new HashMap<>(), order));

        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.findAll();
        assertEquals(2, result.size());
    }
}