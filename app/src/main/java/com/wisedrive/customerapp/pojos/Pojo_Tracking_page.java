package com.wisedrive.customerapp.pojos;

public class Pojo_Tracking_page {
    int imv_request;
    String tv_request,tv_request_description,tv_date,id,tv_otp;
    private int color;


    public Pojo_Tracking_page(int imv_request, String tv_request, String tv_request_description, String tv_date ,String id,int color,String tv_otp) {
        this.imv_request = imv_request;
        this.tv_request = tv_request;
        this.tv_request_description = tv_request_description;
        this.tv_date = tv_date;
        this.id=id;
        this.color = color;
        this.tv_otp=tv_otp;
    }

    public int getImv_request() {
        return imv_request;
    }

    public void setImv_request(int imv_request) {
        this.imv_request = imv_request;
    }

    public String getTv_request() {
        return tv_request;
    }

    public void setTv_request(String tv_request) {
        this.tv_request = tv_request;
    }

    public String getTv_request_description() {
        return tv_request_description;
    }

    public void setTv_request_description(String tv_request_description) {
        this.tv_request_description = tv_request_description;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public String getTv_otp() {
        return tv_otp;
    }

    public void setTv_otp(String tv_otp) {
        this.tv_otp = tv_otp;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
