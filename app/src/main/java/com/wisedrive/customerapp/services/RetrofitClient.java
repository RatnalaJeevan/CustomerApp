package com.wisedrive.customerapp.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static String BASE_URL = "https://reqres.in/";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://164.52.217.96:30022/")
                    //live with domain name  and without domain name
                    //.baseUrl("https://customerappapis.wisedrive.in/")
                    //.baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
