package com.wisedrive.customerapp.pojos;

public class Pojo_Comprehensive_Plans {
    String extended_warranty,text_service_maintanance,text_view_rupee,text_view_percent;

    public Pojo_Comprehensive_Plans(String extended_warranty, String text_service_maintanance, String text_view_rupee, String text_view_percent) {
        this.extended_warranty = extended_warranty;
        this.text_service_maintanance = text_service_maintanance;
        this.text_view_rupee = text_view_rupee;
        this.text_view_percent = text_view_percent;
    }

    public String getExtended_warranty() {
        return extended_warranty;
    }

    public void setExtended_warranty(String extended_warranty) {
        this.extended_warranty = extended_warranty;
    }

    public String getText_service_maintanance() {
        return text_service_maintanance;
    }

    public void setText_service_maintanance(String text_service_maintanance) {
        this.text_service_maintanance = text_service_maintanance;
    }

    public String getText_view_rupee() {
        return text_view_rupee;
    }

    public void setText_view_rupee(String text_view_rupee) {
        this.text_view_rupee = text_view_rupee;
    }

    public String getText_view_percent() {
        return text_view_percent;
    }

    public void setText_view_percent(String text_view_percent) {
        this.text_view_percent = text_view_percent;
    }
}
