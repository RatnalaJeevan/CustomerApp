package com.wisedrive.customerapp.pojos;

public class Pojo_Class_Mycar {
    String text_make,text_model,text_kms,text_transmission,text_fuel_type;
    int car_image,image_kms,image_transmission,image_fuel;

    public Pojo_Class_Mycar(String text_make,String text_model,String text_kms,String text_transmission,String text_fuel_type,int car_image,int image_kms,int image_transmission,int image_fuel) {
        this.text_make = text_make;
        this.text_model=text_model;
        this.text_kms=text_kms;
        this.text_transmission=text_transmission;
        this.text_fuel_type=text_fuel_type;
        this.car_image=car_image;
        this.image_kms=image_kms;
        this.image_transmission=image_transmission;
        this.image_fuel=image_fuel;
    }

    public String getText_make() {
        return text_make;
    }

    public void setText_make(String text_make) {
        this.text_make = text_make;
    }

    public String getText_model() {
        return text_model;
    }

    public void setText_model(String text_model) {
        this.text_model = text_model;
    }

    public String getText_kms() {
        return text_kms;
    }

    public void setText_kms(String text_kms) {
        this.text_kms = text_kms;
    }

    public String getText_transmission() {
        return text_transmission;
    }

    public void setText_transmission(String text_transmission) {
        this.text_transmission = text_transmission;
    }

    public String getText_fuel_type() {
        return text_fuel_type;
    }

    public void setText_fuel_type(String text_date) {
        this.text_fuel_type = text_fuel_type;
    }

    public int getCar_image() {
        return car_image;
    }

    public void setCar_image(int car_image) {
        this.car_image = car_image;
    }

    public int getImage_kms() {
        return image_kms;
    }

    public void setImage_kms(int image_kms) {
        this.image_kms = image_kms;
    }

    public int getImage_transmission() {
        return image_transmission;
    }

    public void setImage_transmission(int image_transmission) {
        this.image_transmission = image_transmission;
    }
    public int getImage_fuel() {
        return image_fuel;
    }

    public void setImage_fuel(int image_fuel) {
        this.image_fuel = image_fuel;
    }



}
