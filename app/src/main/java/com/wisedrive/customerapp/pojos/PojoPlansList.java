package com.wisedrive.customerapp.pojos;

public class PojoPlansList {

    String plan_id;
    String plan_name;

    public PojoPlansList(String plan_id, String plan_name) {
        this.plan_id = plan_id;
        this.plan_name = plan_name;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public String getPlan_name() {
        return plan_name;
    }
}
