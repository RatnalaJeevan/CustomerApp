package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoLostItems {
    @SerializedName("name")
    String name;
    @SerializedName("id")
    String id;
   public String IsSelected = "n";



        public String getName() {
            return name;
        }


    public String getIsSelected() {
        return IsSelected;
    }

    public void setIsSelected(String isSelected) {
        IsSelected = isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
