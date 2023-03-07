package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAppUpdateDetails {
    @SerializedName("can_skip")
    String can_skip;
    @SerializedName("appversion")
    String appversion;
    @SerializedName("app_url")
    String app_url;
    @SerializedName("is_current")
    String is_current;
    @SerializedName("ostype_id")
    String ostype_id;
    @SerializedName("app_name")
    String app_name;
    @SerializedName("application_id")
    String application_id;
    @SerializedName("terms")
    String terms;

    public String getTerms() {
        return terms;
    }

    public String getCan_skip() {
        return can_skip;
    }

    public void setCan_skip(String can_skip) {
        this.can_skip = can_skip;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getIs_current() {
        return is_current;
    }

    public void setIs_current(String is_current) {
        this.is_current = is_current;
    }

    public String getOstype_id() {
        return ostype_id;
    }

    public void setOstype_id(String ostype_id) {
        this.ostype_id = ostype_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }
}
