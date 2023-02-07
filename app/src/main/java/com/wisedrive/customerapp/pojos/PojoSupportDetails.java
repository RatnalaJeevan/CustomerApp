package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoSupportDetails {
    @SerializedName("customer_support_name")
    private String customer_support_name;
    @SerializedName("phone_no")
    private String phone_no;
    @SerializedName("email_id")
    private String email_id;

    public String getCustomer_support_name() {
        return customer_support_name;
    }

    public void setCustomer_support_name(String customer_support_name) {
        this.customer_support_name = customer_support_name;
    }

    public String getCustomer_support_phone_no() {
        return phone_no;
    }

    public void setCustomer_support_phone_no(String customer_support_phone_no) {
        this.phone_no = customer_support_phone_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
}
