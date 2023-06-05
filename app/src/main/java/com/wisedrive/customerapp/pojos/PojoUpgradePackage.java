package com.wisedrive.customerapp.pojos;

public class PojoUpgradePackage {


    private String productId;
    private String addonId;
    private String mainPackId;
    private String subpackId;
    private double amount;
    private String payStatus;
    private String payType;
    private String mode;
    private String refNo;
    private String orderId;
    private String paymentId;
    private double discount;
    private String gatewayId;
    private String leadId;
    private String vehicleId;
    private String customerId;
    private String leadVehicleId;
    private String withpack;
    private String ismultipleaddon;
    private String deliveryNote;
    private String salesReceipt;
    private String couponId;
    private String couponTypeId;
    private double couponAmount;


    public PojoUpgradePackage(String productId, String addonId, String mainPackId, String subpackId,
                              double amount, String payStatus, String payType, String mode, String refNo,
                              String orderId, String paymentId, double discount, String gatewayId, String leadId,
                              String vehicleId, String customerId, String leadVehicleId, String withpack,
                              String ismultipleaddon, String deliveryNote, String salesReceipt, String couponId,
                              String couponTypeId, double couponAmount) {
        this.productId = productId;
        this.addonId = addonId;
        this.mainPackId = mainPackId;
        this.subpackId = subpackId;
        this.amount = amount;
        this.payStatus = payStatus;
        this.payType = payType;
        this.mode = mode;
        this.refNo = refNo;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.discount = discount;
        this.gatewayId = gatewayId;
        this.leadId = leadId;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.leadVehicleId = leadVehicleId;
        this.withpack = withpack;
        this.ismultipleaddon = ismultipleaddon;
        this.deliveryNote = deliveryNote;
        this.salesReceipt = salesReceipt;
        this.couponId = couponId;
        this.couponTypeId = couponTypeId;
        this.couponAmount = couponAmount;
    }
}
