package com.wisedrive.customerapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoVehDetails extends AppResponse {

            @SerializedName("image")
            private String image;
            @SerializedName("customer_name")
            private String customer_name;
            @SerializedName("vehicle_no")
            private String vehicle_no;
            @SerializedName("phone_no")
            private String phone_no;
            @SerializedName("vehicle_id")
            private String vehicle_id;
            @SerializedName("vehicle_make")
            private String vehicle_make;
            @SerializedName("vehicle_model")
            private String vehicle_model;
            @SerializedName("valid_to")
            private String valid_to;
            @SerializedName("brand_icon")
            private String brand_icon;


            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name(String customer_name) {
                this.customer_name = customer_name;
            }

            public String getVehicle_no() {
                return vehicle_no;
            }

            public void setVehicle_no(String vehicle_no) {
                this.vehicle_no = vehicle_no;
            }

            public String getPhone_no() {
                return phone_no;
            }

            public void setPhone_no(String phone_no) {
                this.phone_no = phone_no;
            }

            public String getVehicle_id() {
                return vehicle_id;
            }

            public void setVehicle_id(String vehicle_id) {
                this.vehicle_id = vehicle_id;
            }

            public String getVehicle_make() {
                return vehicle_make;
            }

            public void setVehicle_make(String vehicle_make) {
                this.vehicle_make = vehicle_make;
            }

            public String getVehicle_model() {
                return vehicle_model;
            }

            public void setVehicle_model(String vehicle_model) {
                this.vehicle_model = vehicle_model;
            }

            public String getValid_to() {
                return valid_to;
            }

            public void setValid_to(String valid_to) {
                this.valid_to = valid_to;
            }

            public String getBrand_icon() {
                return brand_icon;
            }

            public void setBrand_icon(String brand_icon) {
                this.brand_icon = brand_icon;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
}
