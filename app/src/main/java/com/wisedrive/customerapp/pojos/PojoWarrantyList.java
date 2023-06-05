package com.wisedrive.customerapp.pojos;

import java.util.ArrayList;

public class PojoWarrantyList {

    String display_name;
    String isSelected="n";
    String package_id;
    ArrayList<PojoSpecificPacList> ProductList;



    public String getPackage_id() {
        return package_id;
    }

    public ArrayList<PojoSpecificPacList> getProductList() {
        return ProductList;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
