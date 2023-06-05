package com.wisedrive.customerapp.pojos;

import android.net.Uri;

public class PojoCarVideoList {

    String module_id;
    String part_name;
    String sample_video;
    String video;
    String video_part_id;
    String video_length;
    private Uri taken_video = null;
    private String filename = "";


    public Uri getTaken_video() {
        return taken_video;
    }

    public String getVideo_length() {
        return video_length;
    }

    public void setTaken_video(Uri taken_video) {
        this.taken_video = taken_video;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getModule_id() {
        return module_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public String getSample_video() {
        return sample_video;
    }

    public String getVideo() {
        return video;
    }

    public String getVideo_part_id() {
        return video_part_id;
    }
}
