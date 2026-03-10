package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.order = order;
        this.paymentData = paymentData;

        if (!method.equals("CASH_ON_DELIVERY") && !method.equals("VOUCHER")) {
            throw new IllegalArgumentException();
        }
        this.method = method;

        if (method.equals("CASH_ON_DELIVERY")) {
            String address = paymentData.get("deliveryAddress");
            String city = paymentData.get("city");
            if (address == null || address.isEmpty() || city == null || city.isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "SUCCESS";
            }
        } else {
            this.status = "WAITING_PAYMENT";
        }
    }

    public Payment(String id, String method, Map<String, String> paymentData, Order order, String status) {
        this(id, method, paymentData, order);
        this.setStatus(status);
    }

    public void setStatus(String status) {
        String[] statusList = {"WAITING_PAYMENT", "SUCCESS", "REJECTED"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }
}