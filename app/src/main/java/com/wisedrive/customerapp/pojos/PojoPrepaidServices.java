package com.wisedrive.customerapp.pojos;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

public class PojoPrepaidServices {
    @SerializedName("part_no")
    String part_no;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("part_name")
    String part_name;

    public String getPart_no() {
        return part_no;
    }

    public void setPart_no(String part_no) {
        this.part_no = part_no;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }
}
