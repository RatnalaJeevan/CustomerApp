package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pojo_Combo_Plans {
    @SerializedName("is_starter_pack")
    String is_starter_pack;
    @SerializedName("amount_saved")
    double amount_saved;
    @SerializedName("start_color")
    String start_color;
    @SerializedName("display_name")
    String display_name;
    @SerializedName("package_id")
    String package_id;
    @SerializedName("end_color")
    String end_color;
    @SerializedName("original_price")
    String original_price;
    @SerializedName("percentage_amount_saved")
    String percentage_amount_saved;
    @SerializedName("package_type")
    String package_type;
    @SerializedName("final_price")
    double final_price;
    @SerializedName("package_type_name")
    String package_type_name;
    @SerializedName("package_type_name_id")
    String package_type_name_id;


    @SerializedName("ProductList")
    ArrayList<PojoComboProducts> ProductList;
    @SerializedName("main_package_id")
    String main_package_id;

    @SerializedName("package_logo")
    String package_logo;


    @SerializedName("Recomendation")
    ArrayList<PojoEWPRc> Recomendation;

    @SerializedName("ActiveVeh")
    ArrayList<PojoEWPRc> ActiveVeh;

    public String getPackage_logo() {
        return package_logo;
    }

    public String getPackage_type_name_id() {
        return package_type_name_id;
    }

    public ArrayList<PojoEWPRc> getActiveVeh() {
        return ActiveVeh;
    }

    public ArrayList<PojoEWPRc> getRecomendation() {
        return Recomendation;
    }

    public String getPackage_type_name() {
        return package_type_name;
    }

    public double getFinal_price() {
        return final_price;
    }

    public String getMain_package_id() {
        return main_package_id;
    }

    public String getIs_starter_pack() {
        return is_starter_pack;
    }

    public double getAmount_saved() {
        return amount_saved;
    }

    public String getStart_color() {
        return start_color;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public String getEnd_color() {
        return end_color;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public String getPercentage_amount_saved() {
        return percentage_amount_saved;
    }

    public String getPackage_type() {
        return package_type;
    }

    public ArrayList<PojoComboProducts> getProductList() {
        return ProductList;
    }
}
