package com.wisedrive.customerapp.pojos;

import android.net.Uri;

public class PojoCarImgs {

    String image;
    String image_part_id;
    String module_id;
    String sample_image;
    String part_name;


    private Uri taken_img = null;
    private String filename = "";


    public Uri getTaken_img() {
        return taken_img;
    }

    public void setTaken_img(Uri taken_img) {
        this.taken_img = taken_img;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImage() {
        return image;
    }

    public String getImage_part_id() {
        return image_part_id;
    }

    public String getModule_id() {
        return module_id;
    }

    public String getSample_image() {
        return sample_image;
    }

    public String getPart_name() {
        return part_name;
    }
}
