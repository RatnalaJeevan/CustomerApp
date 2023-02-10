package com.wisedrive.customerapp.pojos;

public class Pojo_Additional_Services {
    int image_logo;
    String tv_additional_service_plan;

    public Pojo_Additional_Services(int image_logo, String tv_additional_service_plan) {
        this.image_logo = image_logo;
        this.tv_additional_service_plan = tv_additional_service_plan;
    }

    public int getImage_logo() {
        return image_logo;
    }

    public void setImage_logo(int image_logo) {
        this.image_logo = image_logo;
    }

    public String getTv_additional_service_plan() {
        return tv_additional_service_plan;
    }

    public void setTv_additional_service_plan(String tv_additional_service_plan) {
        this.tv_additional_service_plan = tv_additional_service_plan;
    }
}
