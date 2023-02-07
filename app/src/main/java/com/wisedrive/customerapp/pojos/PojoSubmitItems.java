package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoSubmitItems {
    @SerializedName("itemarr")
    private ArrayList<ItemsList> itemarr;

    public PojoSubmitItems(ArrayList<ItemsList> itemarr) {
        this.itemarr = itemarr;
    }
}
