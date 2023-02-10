package com.wisedrive.customerapp.pojos;

public class Pojo_Service_Includes {
    String text_service_name,tv_description;
    int image_logo;

    public Pojo_Service_Includes(String text_service_name, String tv_description, int image_logo) {
        this.text_service_name = text_service_name;
        this.tv_description = tv_description;
        this.image_logo = image_logo;
    }

    public String getText_service_name() {
        return text_service_name;
    }

    public void setText_service_name(String text_service_name) {
        this.text_service_name = text_service_name;
    }

    public String getTv_description() {
        return tv_description;
    }

    public void setTv_description(String tv_description) {
        this.tv_description = tv_description;
    }

    public int getImage_logo() {
        return image_logo;
    }

    public void setImage_logo(int image_logo) {
        this.image_logo = image_logo;
    }
}
