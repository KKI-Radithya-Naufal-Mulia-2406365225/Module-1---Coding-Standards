package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    Payment createPayment(Order order, String method, Map<String, String> paymentData);
    Payment updateStatus(String paymentId, String status);
    Payment findById(String paymentId);
    List<Payment> findAll();
}