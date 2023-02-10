package com.wisedrive.customerapp.pojos;

public class Pojo_Upload_Image {
    String tv_image_name;
    int uplaod_image;

    public Pojo_Upload_Image(String tv_image_name, int uplaod_image) {
        this.tv_image_name = tv_image_name;
        this.uplaod_image = uplaod_image;
    }

    public String getTv_image_name() {
        return tv_image_name;
    }

    public void setTv_image_name(String tv_image_name) {
        this.tv_image_name = tv_image_name;
    }

    public int getUplaod_image() {
        return uplaod_image;
    }

    public void setUplaod_image(int uplaod_image) {
        this.uplaod_image = uplaod_image;
    }
}
