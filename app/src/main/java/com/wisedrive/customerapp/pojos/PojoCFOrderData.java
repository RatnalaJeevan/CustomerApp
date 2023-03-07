package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoCFOrderData {
    @SerializedName("order_status")
    String order_status;
    @SerializedName("cf_order_id")
    String cf_order_id;
    @SerializedName("payment_session_id")
    String payment_session_id;
    @SerializedName("order_id")
    String order_id;

    public String getOrder_status() {
        return order_status;
    }

    public String getCf_order_id() {
        return cf_order_id;
    }

    public String getPayment_session_id() {
        return payment_session_id;
    }

    public String getOrder_id() {
        return order_id;
    }
}
