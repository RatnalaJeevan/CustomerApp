package com.wisedrive.customerapp.pojos;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Pojo_initiate_Claims_Photos {
    @SerializedName("image_type_id")
    private String image_type_id;
    @SerializedName("image_type")
    private String image_type;
    private Uri image = null;
    private  String filename = "";
    private  String imageurl = "";
    private Bitmap bitmap;

    public String getImage_type_id() {
        return image_type_id;
    }

    public void setImage_type_id(String image_type_id) {
        this.image_type_id = image_type_id;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}


