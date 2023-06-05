package com.wisedrive.customerapp.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                //local
               // .baseUrl("http://192.168.0.30:30022")

                 //test
                .baseUrl("http://164.52.217.96:30022/")

                //live with domain name  and without domain name
                // .baseUrl("https://customerappapis.wisedrive.in/")

                //live with domain name  and with domain name
                //.baseUrl("http://164.52.208.170:30030")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }
}
