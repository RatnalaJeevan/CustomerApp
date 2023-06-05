package com.wisedrive.customerapp.pojos;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PojoUpgrade {

    String package_logo;
    double final_price;
    String package_name;
    String package_id;

    ArrayList<PojoSpecificPacList> ProductList;
    String   isSelected="n";

    public ArrayList<PojoSpecificPacList> getProductList() {
        return ProductList;
    }

    PojoPayAsYou PayAsyouGoEligibility;

    public PojoPayAsYou getPayAsyouGoEligibility() {
        return PayAsyouGoEligibility;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public String getPackage_logo() {
        return package_logo;
    }

    public double getFinal_price() {
        return final_price;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getPackage_id() {
        return package_id;
    }

    public class PojoPayAsYou{
        int percentage_amount_to_pay;
        String is_eligible;

        public int getPercentage_amount_to_pay() {
            return percentage_amount_to_pay;
        }

        public String getIs_eligible() {
            return is_eligible;
        }
    }
}
