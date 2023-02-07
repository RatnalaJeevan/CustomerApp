package com.wisedrive.customerapp.commonclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ResponseExtension {

    private JSONObject response;

    public ResponseExtension(JSONObject response) {
        this.response = response;
    }

    public String getResponseType() {
        try {
            return this.response.getString("responseType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getResponseMessage() {
        try {
            return this.response.getString("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONObject getResponseObject() {
        try {
            return this.response.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public JSONArray getResponseArray() {
        try {
            return this.response.getJSONArray("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
