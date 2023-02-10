package com.wisedrive.customerapp.pojos;

public class Pojo_Class_Addons_List {

    String tv_status_amount,tv_select,tv_warranty_name,tv_description,tv_scratch_amount,tv_amount,tv_percent,id;

    public Pojo_Class_Addons_List(String tv_status_amount, String tv_select, String tv_warranty_name, String tv_description, String tv_scratch_amount, String tv_amount, String tv_percent,String id) {
        this.tv_status_amount = tv_status_amount;
        this.tv_select = tv_select;
        this.tv_warranty_name = tv_warranty_name;
        this.tv_description = tv_description;
        this.tv_scratch_amount = tv_scratch_amount;
        this.tv_amount = tv_amount;
        this.tv_percent = tv_percent;
        this.id=id;

    }

    public String getTv_status_amount() {
        return tv_status_amount;
    }

    public void setTv_status_amount(String tv_status_amount) {
        this.tv_status_amount = tv_status_amount;
    }

    public String getTv_select() {
        return tv_select;
    }

    public void setTv_select(String tv_select) {
        this.tv_select = tv_select;
    }

    public String getTv_warranty_name() {
        return tv_warranty_name;
    }

    public void setTv_warranty_name(String tv_warranty_name) {
        this.tv_warranty_name = tv_warranty_name;
    }

    public String getTv_description() {
        return tv_description;
    }

    public void setTv_description(String tv_description) {
        this.tv_description = tv_description;
    }

    public String getTv_scratch_amount() {
        return tv_scratch_amount;
    }

    public void setTv_scratch_amount(String tv_scratch_amount) {
        this.tv_scratch_amount = tv_scratch_amount;
    }

    public String getTv_amount() {
        return tv_amount;
    }

    public void setTv_amount(String tv_amount) {
        this.tv_amount = tv_amount;
    }

    public String getTv_percent() {
        return tv_percent;
    }

    public void setTv_percent(String tv_percent) {
        this.tv_percent = tv_percent;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
