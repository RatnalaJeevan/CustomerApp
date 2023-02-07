package com.wisedrive.customerapp.commonclasses;

public interface ResponseListener {
    public void ResponseSuccess(ResponseExtension response);
    public void ResponseFailure(int responseCode, String errorMsg);
}
