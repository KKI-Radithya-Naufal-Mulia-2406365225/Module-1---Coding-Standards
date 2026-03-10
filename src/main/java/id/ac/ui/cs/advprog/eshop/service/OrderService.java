package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import java.util.List;

public interface OrderService {
    public Order createOrder(Order order);
    public Order updateStatus(String orderId, String status);
    public List<Order> findAllByAuthor(String author);
    public Order findById(String orderId);
}