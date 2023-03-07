package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class Pojo_Tracking_page {

    @SerializedName("service_flow_date")
    String service_flow_date;
    @SerializedName("status_id")
    String status_id;
    @SerializedName("status_name")
    String status_name;
    @SerializedName("created_on")
    String created_on;
    @SerializedName("service_type")
    String service_type;
    @SerializedName("happy_code")
    String happy_code;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("comments")
    String comments;
    @SerializedName("service_status_id")
    String service_status_id;
    @SerializedName("service_date")
    String service_date;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("otp")
    String otp;
    @SerializedName("icon")
    String icon;
    @SerializedName("description")
    String description;
    @SerializedName("is_active")
    String is_active;

    public String getIs_active() {
        return is_active;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getService_status_id() {
        return service_status_id;
    }

    public String getOtp() {
        return otp;
    }

    public String getService_flow_date() {
        return service_flow_date;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getService_type() {
        return service_type;
    }

    public String getHappy_code() {
        return happy_code;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getComments() {
        return comments;
    }

    public String getId() {
        return service_status_id;
    }

    public String getService_date() {
        return service_date;
    }

    public String getPackage_name() {
        return package_name;
    }
}
