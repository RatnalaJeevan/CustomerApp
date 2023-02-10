package com.wisedrive.customerapp.pojos;

public class Pojo_Showroom_services {
    String text_service_name,tv_description,tv_booked_on,tv_date,tv_track,id;
    int image_logo;


    public Pojo_Showroom_services(String text_service_name, String tv_description, String tv_booked_on, String tv_date, String tv_track, int image_logo,String id) {
        this.text_service_name = text_service_name;
        this.tv_description = tv_description;
        this.tv_booked_on = tv_booked_on;
        this.tv_date = tv_date;
        this.tv_track = tv_track;
        this.image_logo = image_logo;
        this.id=id;

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

    public String getTv_booked_on() {
        return tv_booked_on;
    }

    public void setTv_booked_on(String tv_booked_on) {
        this.tv_booked_on = tv_booked_on;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_track() {
        return tv_track;
    }

    public void setTv_track(String tv_track) {
        this.tv_track = tv_track;
    }

    public int getImage_logo() {
        return image_logo;
    }

    public void setImage_logo(int image_icon) {
        this.image_logo = image_logo;
    }

    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }
}
