package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAddServiceList {


    @SerializedName("comments")
    String comments;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("service_on_date")
    String service_on_date;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("service_id")
    String service_id;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("status_id")
    String status_id;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getService_on_date() {
        return service_on_date;
    }

    public void setService_on_date(String service_on_date) {
        this.service_on_date = service_on_date;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
