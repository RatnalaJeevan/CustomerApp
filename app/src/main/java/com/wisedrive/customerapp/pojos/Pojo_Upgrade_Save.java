package com.wisedrive.customerapp.pojos;

public class Pojo_Upgrade_Save {
    int image;
    String upgrade_list;

    public Pojo_Upgrade_Save(int image, String upgrade_list) {
        this.image = image;
        this.upgrade_list = upgrade_list;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUpgrade_list() {
        return upgrade_list;
    }

    public void setUpgrade_list(String upgrade_list) {
        this.upgrade_list = upgrade_list;
    }
}
