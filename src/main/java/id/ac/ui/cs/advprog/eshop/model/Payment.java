package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.order = order;
        this.paymentData = paymentData;

        String[] methodList = {"VOUCHER", "CASH_ON_DELIVERY"};
        if (Arrays.stream(methodList).noneMatch(item -> item.equals(method))) {
            throw new IllegalArgumentException();
        }
        this.method = method;

        if (method.equals("VOUCHER")) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucher(voucherCode)) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            String address = paymentData.get("deliveryAddress");
            String city = paymentData.get("city");
            if (address == null || address.isEmpty() || city == null || city.isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "SUCCESS";
            }
        }
    }

    public Payment(String id, String method, Map<String, String> paymentData, Order order, String status) {
        this(id, method, paymentData, order);
        this.setStatus(status);
    }

    private boolean isValidVoucher(String code) {
        if (code == null || code.length() != 16 || !code.startsWith("PBP")) {
            return false;
        }
        int digitCount = 0;
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }
        return digitCount == 8;
    }

    public void setStatus(String status) {
        String[] statusList = {"WAITING_PAYMENT", "SUCCESS", "REJECTED"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException();
        }
        this.status = status;
    }
}