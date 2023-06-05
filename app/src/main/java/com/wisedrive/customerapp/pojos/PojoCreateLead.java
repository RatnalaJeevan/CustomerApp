package com.wisedrive.customerapp.pojos;

public class PojoCreateLead {
    String name;
    String email;
    String phoneNo;

    String customerId;
    String leadId;
    String emailId;

    public PojoCreateLead(String name, String email, String phoneNo) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public PojoCreateLead(String name, String phoneNo, String customerId, String leadId, String emailId) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.customerId = customerId;
        this.leadId = leadId;
        this.emailId = emailId;
    }
}
