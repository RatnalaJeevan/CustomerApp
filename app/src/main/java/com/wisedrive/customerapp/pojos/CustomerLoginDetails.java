package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class CustomerLoginDetails {

    @SerializedName("phone_no_1")
    private String phone_no_1;
    @SerializedName("customer_id_1")
    private String customer_id_1;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_no")
    private String customer_no;

    @SerializedName("lead_id")
    String lead_id;
    @SerializedName("is_package_activated")
    String is_package_activated;
    @SerializedName("email_id")
    String email_id;

    @SerializedName("aws_secret")
    String aws_secret;
    @SerializedName("aws_key")
    String aws_key;
    @SerializedName("comet_auth_key")
    String comet_auth_key;
    @SerializedName("comet_region")
    String comet_region;
    @SerializedName("comet_app_id")
    String comet_app_id;

    public String getCustomer_no() {
        return customer_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getIs_package_activated() {
        return is_package_activated;
    }

    public String getLead_id() {
        return lead_id;
    }

    public String getAws_secret() {
        return aws_secret;
    }

    public void setAws_secret(String aws_secret) {
        this.aws_secret = aws_secret;
    }

    public String getAws_key() {
        return aws_key;
    }

    public void setAws_key(String aws_key) {
        this.aws_key = aws_key;
    }

    public String getComet_auth_key() {
        return comet_auth_key;
    }

    public void setComet_auth_key(String comet_auth_key) {
        this.comet_auth_key = comet_auth_key;
    }

    public String getComet_region() {
        return comet_region;
    }

    public void setComet_region(String comet_region) {
        this.comet_region = comet_region;
    }

    public String getComet_app_id() {
        return comet_app_id;
    }

    public void setComet_app_id(String comet_app_id) {
        this.comet_app_id = comet_app_id;
    }
    public String getPhone_no_1() {
        return phone_no_1;
    }

    public void setPhone_no_1(String phone_no_1) {
        this.phone_no_1 = phone_no_1;
    }

    public String getCustomer_id_1() {
        return customer_id_1;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id_1(String customer_id_1) {
        this.customer_id_1 = customer_id_1;
    }
}
