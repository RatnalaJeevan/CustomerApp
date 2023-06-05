package com.wisedrive.customerapp.pojos;

public class PojoPayParttial {

    String dppId;
    double amount;
    String payStatus;
    String payType;
    String mode;
    String refNo;
    String orderId;
    String paymentId;
    double discount;
    String gatewayId;

    public PojoPayParttial(String dppId, double amount, String payStatus, String payType,
                           String mode, String refNo, String orderId, String paymentId,
                           double discount, String gatewayId) {
        this.dppId = dppId;
        this.amount = amount;
        this.payStatus = payStatus;
        this.payType = payType;
        this.mode = mode;
        this.refNo = refNo;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.discount = discount;
        this.gatewayId = gatewayId;
    }
}
