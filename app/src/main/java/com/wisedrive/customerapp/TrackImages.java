package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.wisedrive.customerapp.adapters.AdapterInvoicesList;
import com.wisedrive.customerapp.adapters.AdapterTrackImages;
import com.wisedrive.customerapp.pojos.PojoTrackImages;

import java.util.ArrayList;

public class TrackImages extends AppCompatActivity {

    RecyclerView rv_images_list;
    TextView comments;
    ArrayList<PojoTrackImages> pojoTrackImages;
    AdapterTrackImages adapterTrackImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_images);
        rv_images_list= findViewById(R.id.rv_images_list);
        comments= findViewById(R.id.comments);

        pojoTrackImages=new ArrayList<>();
        pojoTrackImages.add(new PojoTrackImages(R.drawable.icon_noimage));
        pojoTrackImages.add(new PojoTrackImages(R.drawable.icon_noimage));
        pojoTrackImages.add(new PojoTrackImages(R.drawable.icon_noimage));
        pojoTrackImages.add(new PojoTrackImages(R.drawable.icon_noimage));
        adapterTrackImages= new AdapterTrackImages(pojoTrackImages, TrackImages.this);
        GridLayoutManager layoutManager = new GridLayoutManager(TrackImages.this, 2);
        rv_images_list.setLayoutManager(layoutManager);
        rv_images_list.setAdapter(adapterTrackImages);

    }
}