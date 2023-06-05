package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_My_Car_page_package_list {

    @SerializedName("product_id")
    String product_id;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("product_name")
    String product_name;
    @SerializedName("description")
    String description;
    @SerializedName("valid_to")
    String valid_to;
    @SerializedName("product_icon")
    String product_icon;
    @SerializedName("final_amount")
    String final_amount;
    @SerializedName("validity_days")
    String validity_days;
    @SerializedName("is_warranty")
    String is_warranty;
    @SerializedName("expiry_date")
    String expiry_date;
    @SerializedName("is_active")
    String is_active;
    @SerializedName("is_service")
    String is_service;
    @SerializedName("InspectionRequested")
    String InspectionRequested;
    @SerializedName("color")
    String color;
    @SerializedName("error_msg")
    String error_msg;
    @SerializedName("package_type")
    String package_type;
    @SerializedName("expired")
    String expired;
    @SerializedName("AcivationCode")
    String AcivationCode;
    @SerializedName("dpp_id")
    String dpp_id;
    @SerializedName("Displaymessage")
    String Displaymessage;
    @SerializedName("ButtonName")
    String ButtonName;
    @SerializedName("ServiceCount")
    PojoServiceCount ServiceCount;

    public String getDisplaymessage() {
        return Displaymessage;
    }

    public String getButtonName() {
        return ButtonName;
    }

    public String getDpp_id() {
        return dpp_id;
    }

    public String getExpired() {
        return expired;
    }

    public String getAcivationCode() {
        return AcivationCode;
    }

    public String getPackage_type() {
        return package_type;
    }

    public PojoServiceCount getServiceCount() {
        return ServiceCount;
    }

    public String getColor() {
        return color;
    }

    public String getError_msg() {
        return error_msg;
    }

    public String getInspectionRequested() {
        return InspectionRequested;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getIs_service() {
        return is_service;
    }

    public String getIs_warranty() {
        return is_warranty;
    }

    public String getValid_to() {
        return valid_to;
    }

    public String getProduct_icon() {
        return product_icon;
    }

    public String getFinal_amount() {
        return final_amount;
    }

    public String getValidity_days() {
        return validity_days;
    }

    public String getDescription() {
        return description;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getProduct_name() {
        return product_name;
    }


    public class PojoServiceCount {

        @SerializedName("pending_count")
        String pending_count;
        @SerializedName("product_name")
        String product_name;

        public String getPending_count() {
            return pending_count;
        }

        public String getProduct_name() {
            return product_name;
        }
    }
}
