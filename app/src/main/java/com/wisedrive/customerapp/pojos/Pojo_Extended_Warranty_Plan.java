package com.wisedrive.customerapp.pojos;

public class Pojo_Extended_Warranty_Plan {

    String text_warranty_name,text_amount,text_save_amount;


    public Pojo_Extended_Warranty_Plan(String text_warranty_name, String text_amount, String text_save_amount) {
        this.text_warranty_name = text_warranty_name;
        this.text_amount = text_amount;
        this.text_save_amount =text_save_amount;

    }

    public String getText_warranty_name() {
        return text_warranty_name;
    }

    public void setText_warranty_name(String text_warranty_name) {
        this.text_warranty_name = text_warranty_name;
    }

    public String getText_amount() {
        return text_amount;
    }

    public void setText_amount(String text_amount) {
        this.text_amount = text_amount;
    }

    public String getText_save_amount() {
        return text_save_amount;
    }

    public void setText_save_amount(String text_save_amount) {
        this.text_save_amount = text_save_amount;
    }


}
