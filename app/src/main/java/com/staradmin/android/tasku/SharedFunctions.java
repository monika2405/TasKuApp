package com.staradmin.android.tasku;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SharedFunctions {
    private static final String URL = "https://projectlab.co.id/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
