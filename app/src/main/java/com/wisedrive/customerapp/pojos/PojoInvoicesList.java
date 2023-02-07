package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoInvoicesList {

    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("invoice_image")
    String invoice_image;

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getInvoice_image() {
        return invoice_image;
    }

    public void setInvoice_image(String invoice_image) {
        this.invoice_image = invoice_image;
    }
}
