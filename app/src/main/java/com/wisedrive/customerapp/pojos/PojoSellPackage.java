package com.wisedrive.customerapp.pojos;

public class PojoSellPackage {

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
    private String customerId;
    private String leadVehicleId;
    private String withpack;
    private String ismultipleaddon;
    private String deliveryNote;
    private String salesReceipt;
    private String couponId;
    private String couponTypeId;
    private double couponAmount;
    private String vehicleId;
    private String couponCode;
    private String couponCodeTypeId;
    private String addedFrom;
    private String isPartialPayment;
    private double packFullAmount;
    private double packPartialAmount;

    public PojoSellPackage(String productId, String addonId, String mainPackId, String subpackId, double amount,
                           String payStatus, String payType, String mode, String refNo, String orderId,
                           String paymentId, double discount, String gatewayId, String leadId, String customerId,
                           String leadVehicleId, String withpack, String ismultipleaddon, String deliveryNote,
                           String salesReceipt, String couponId, String couponTypeId, double couponAmount, String vehicleId
                           ,String couponCode,String couponCodeTypeId,String addedFrom, String isPartialPayment,
                            double packFullAmount, double packPartialAmount) {
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
        this.customerId = customerId;
        this.leadVehicleId = leadVehicleId;
        this.withpack = withpack;
        this.ismultipleaddon = ismultipleaddon;
        this.deliveryNote = deliveryNote;
        this.salesReceipt = salesReceipt;
        this.couponId = couponId;
        this.couponTypeId = couponTypeId;
        this.couponAmount = couponAmount;
        this.vehicleId = vehicleId;
        this.couponCode=couponCode;
        this.couponCodeTypeId=couponCodeTypeId;
        this.addedFrom=addedFrom;
        this.isPartialPayment=isPartialPayment;
        this.packFullAmount=packFullAmount;
        this.packPartialAmount=packPartialAmount;
    }



}