package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoCouponList {

    @SerializedName("expiration_date")
    String expiration_date;
    @SerializedName("description")
    String description;
    @SerializedName("coupon_id")
    String coupon_id;
    @SerializedName("discount_amount")
    double discount_amount;
    @SerializedName("coupon_code")
    String coupon_code;
    @SerializedName("coupon_type")
    String coupon_type;
    @SerializedName("is_valid")
    String is_valid;

    public String getExpiration_date() {
        return expiration_date;
    }

    public String getDescription() {
        return description;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public double getDiscount_amount() {
        return discount_amount;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public String getIs_valid() {
        return is_valid;
    }
}
