package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoMyPayments {
    @SerializedName("vehicle_make")
    String vehicle_make;
    @SerializedName("amount")
    double amount;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("vehicle_model")
    String vehicle_model;
    @SerializedName("package_name")
    String package_name;
    @SerializedName("package_purchased_on")
    String package_purchased_on;
    @SerializedName("AddonHistory")
    ArrayList<PojoPaidAddonList> AddonHistory;

    @SerializedName("ProductList")
    private ArrayList<Pojo_Service_Includes> ProductList;

    public ArrayList<Pojo_Service_Includes> get_product_list() {
        return ProductList;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public double getAmount() {
        return amount;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_purchased_on() {
        return package_purchased_on;
    }

    public ArrayList<PojoPaidAddonList> getAddonHistory() {
        return AddonHistory;
    }

    public PojoMyPayments() {
    }


}
