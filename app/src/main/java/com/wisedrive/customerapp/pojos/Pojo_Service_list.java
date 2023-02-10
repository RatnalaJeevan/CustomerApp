package com.wisedrive.customerapp.pojos;

public class Pojo_Service_list {
    String Service_name,tv_description,tv_description_lines;
    int image_logo;
    public boolean isVisible ;

    public Pojo_Service_list(String service_name, String tv_description, String tv_description_lines, int image_logo) {
        Service_name = service_name;
        this.tv_description= tv_description;
        this.tv_description_lines = tv_description_lines;
        this.image_logo = image_logo;
        this.isVisible=false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisibility(boolean visibility) {
        this.isVisible = isVisible;

    }

    public String getService_name() {
        return Service_name;
    }

    public void setService_name(String service_name) {
        Service_name = service_name;
    }

    public String getTv_description() {
        return tv_description;
    }

    public void setTv_description(String tv_description) {
        this.tv_description=tv_description;
    }

    public String getTv_description_lines() {
        return tv_description_lines;
    }

    public void setTv_description_lines(String tv_description_lines) {
        this.tv_description_lines = tv_description_lines;
    }

    public int getImage_logo() {
        return image_logo;
    }

    public void setImage_logo(int image_logo) {
        this.image_logo = image_logo;
    }
}
