package com.wisedrive.customerapp.pojos;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;

public class PojoUploadInvoice {

    String vehicleId;
    String customerId;
    ArrayList<PojoImageArray> imagesArr;


    public PojoUploadInvoice(String vehicleId, String customerId, ArrayList<PojoImageArray> imagesArr) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.imagesArr = imagesArr;
    }
}
