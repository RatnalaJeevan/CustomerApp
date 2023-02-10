package com.wisedrive.customerapp.pojos;

public class Pojo_My_Car_page_package_list {
    int image_logo,right_icon;
    String text_warranty_name,tv_description,id;
    private int color;


    public int getImage_logo() {
        return image_logo;
    }

    public void setImage_logo(int image_logo) {
        this.image_logo = image_logo;
    }

    public String getText_warranty_name() {
        return text_warranty_name;
    }

    public void setText_warranty_name(String text_warranty_name) {
        this.text_warranty_name = text_warranty_name;
    }

    public String getTv_description() {
        return tv_description;
    }



    public void setTv_description(String tv_description) {
        this.tv_description = tv_description;
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

    public void setColor(int color) {
        this.color = color;
    }

    public int getRight_icon() {
        return right_icon;
    }

    public void setRight_icon(int right_icon) {
        this.right_icon = right_icon;
    }

    public Pojo_My_Car_page_package_list(int image_logo, String text_warranty_name, String tv_description, String id, int color , int right_icon) {
        this.image_logo = image_logo;
        this.text_warranty_name = text_warranty_name;
        this.tv_description = tv_description;
        this.id=id;
        this.color = color;
        this.right_icon=right_icon;
    }
}
