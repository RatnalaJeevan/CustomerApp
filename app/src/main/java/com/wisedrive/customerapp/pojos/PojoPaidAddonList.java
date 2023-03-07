package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPaidAddonList {
    @SerializedName("addon_name")
    String addon_name;

    public String getAddon_name() {
        return addon_name;
    }
}
