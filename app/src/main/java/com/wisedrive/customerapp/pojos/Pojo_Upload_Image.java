package com.wisedrive.customerapp.pojos;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Pojo_Upload_Image {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    private Uri image = null;
    private  String filename = "";
    private  String imageurl = "";
    private Bitmap bitmap;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Uri getImage() {
        return image;
    }

    public String getFilename() {
        return filename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
