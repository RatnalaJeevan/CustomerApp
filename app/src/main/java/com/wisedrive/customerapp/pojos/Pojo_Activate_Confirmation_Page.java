package com.wisedrive.customerapp.pojos;

public class Pojo_Activate_Confirmation_Page {

    int imv_image;
    String tv_warranty_name,tv_expire_date;

    public Pojo_Activate_Confirmation_Page(int imv_image, String tv_warranty_name, String tv_expire_date) {
        this.imv_image = imv_image;
        this.tv_warranty_name = tv_warranty_name;
        this.tv_expire_date = tv_expire_date;
    }

    public int getImv_image() {
        return imv_image;
    }

    public void setImv_image(int imv_image) {
        this.imv_image = imv_image;
    }

    public String getTv_warranty_name() {
        return tv_warranty_name;
    }

    public void setTv_warranty_name(String tv_warranty_name) {
        this.tv_warranty_name = tv_warranty_name;
    }

    public String getTv_expire_date() {
        return tv_expire_date;
    }

    public void setTv_expire_date(String tv_expire_date) {
        this.tv_expire_date = tv_expire_date;
    }
}
