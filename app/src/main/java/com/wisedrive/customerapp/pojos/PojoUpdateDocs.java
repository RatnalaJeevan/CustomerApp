package com.wisedrive.customerapp.pojos;

public class PojoUpdateDocs {

    String vehId;
    String customerId;
    String leadVehId;
    String leadId;
    String documentId;
    String docImg;

    public PojoUpdateDocs(String vehId, String customerId, String leadVehId, String leadId, String documentId, String docImg) {
        this.vehId = vehId;
        this.customerId = customerId;
        this.leadVehId = leadVehId;
        this.leadId = leadId;
        this.documentId = documentId;
        this.docImg = docImg;
    }
}
