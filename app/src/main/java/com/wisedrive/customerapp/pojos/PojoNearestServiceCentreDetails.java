package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoNearestServiceCentreDetails {
    @SerializedName("service_center_id")
    private String service_center_id;
    @SerializedName("pincode")
    private String pincode;
    @SerializedName("service_center_name")
    private String service_center_name;
    @SerializedName("address")
    private String address;
    @SerializedName("created_on")
    private String created_on;
    @SerializedName("state_id")
    private String state_id;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("service_center_brand_id")
    private String service_center_brand_id;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("is_serving")
    private String is_serving;

    public String getService_center_id() {
        return service_center_id;
    }

    public void setService_center_id(String service_center_id) {
        this.service_center_id = service_center_id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getService_center_name() {
        return service_center_name;
    }

    public void setService_center_name(String service_center_name) {
        this.service_center_name = service_center_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getService_center_brand_id() {
        return service_center_brand_id;
    }

    public void setService_center_brand_id(String service_center_brand_id) {
        this.service_center_brand_id = service_center_brand_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getIs_serving() {
        return is_serving;
    }

    public void setIs_serving(String is_serving) {
        this.is_serving = is_serving;
    }
}
