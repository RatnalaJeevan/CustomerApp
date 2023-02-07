package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAddresses {
    //get adres attributes
    @SerializedName("address_line_2")
    private String address_line_2;
    @SerializedName("pincode")
    private String pincode;
    @SerializedName("created_on")
    private String created_on;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("id")
    private String id;
    @SerializedName("state_id")
    private String state_id;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("address_line_1")
    private String address_line_1;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;

    //post address attributes
    // adrs1,2,landmark,pincode,city,state,customerid
    private String addressLine1;
    private String addressLine2;
    //private String landmark;
    private String pinCode;
    /*private String city;
    private String state;*/
    private String customerId;
    private String location;

    //pincode model attributes
    //1.pincode 2.state 3.city
    @SerializedName("state_name")
    private String state_name;
    @SerializedName("city_name")
    private String city_name;


    public  PojoAddresses( String addressLine1, String addressLine2,
                         String landmark, String pinCode, String city, String state, String customerId,
                           String location) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.landmark = landmark;
        this.pinCode = pinCode;
        this.city = city;
        this.state = state;
        this.customerId = customerId;
        this.location = location;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
