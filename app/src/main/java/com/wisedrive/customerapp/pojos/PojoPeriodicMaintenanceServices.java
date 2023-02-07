package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPeriodicMaintenanceServices {


    String vehicleId;
    String customerId;
    public PojoPeriodicMaintenanceServices(String vehicleId, String customerId) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
    }

}
