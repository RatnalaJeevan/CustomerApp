package com.wisedrive.customerapp.pojos;

public class PojoCreateOrder {
    String customerEmail;
    String customerPhone;
    String customerName;
    double orderAmount;
    String customerId;

    public PojoCreateOrder(String customerEmail, String customerPhone, String customerName, double orderAmount, String customerId) {
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.customerName = customerName;
        this.orderAmount = orderAmount;
        this.customerId = customerId;
    }
}
