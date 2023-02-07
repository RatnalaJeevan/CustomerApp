package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoHappyCode {
    @SerializedName("happy_code")
    private String happy_code;
    @SerializedName("phone_no")
    private String phone_no;
    @SerializedName("service_center_name")
    private String service_center_name;



    public String getHappy_code() {
        return happy_code;
    }

    public void setHappy_code(String happy_code) {
        this.happy_code = happy_code;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getService_center_name() {
        return service_center_name;
    }

    public void setService_center_name(String service_center_name) {
        this.service_center_name = service_center_name;
    }
}
