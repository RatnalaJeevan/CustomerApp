package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoSymptoms {

    @SerializedName("symptom_id")
    String symptom_id;
    @SerializedName("symptom_name")
    String symptom_name;

    String isSelected="n";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }


    public String getSymptom_id() {
        return symptom_id;
    }

    public void setSymptom_id(String symptom_id) {
        this.symptom_id = symptom_id;
    }

    public String getSymptom_name() {
        return symptom_name;
    }

    public void setSymptom_name(String symptom_name) {
        this.symptom_name = symptom_name;
    }
}
