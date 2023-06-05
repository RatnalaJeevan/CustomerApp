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
    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("lead_id")
    private String lead_id;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("customer_id")
    private String customer_id;

    public String getCustomer_name() {
        return customer_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getLead_id() {
        return lead_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getCustomer_id() {
        return customer_id;
    }

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
