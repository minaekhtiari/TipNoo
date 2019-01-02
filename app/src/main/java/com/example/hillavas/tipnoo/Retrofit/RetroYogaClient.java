package com.example.hillavas.tipnoo.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinaPC on 7/20/2017.
 */

public class RetroYogaClient {
    private static final String ROOT_URL ="http://79.175.138.73/";



    public RetroYogaClient() {

    }

    private static Retrofit getRetroYogaClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static IRetrofit getApiService() {
        return getRetroYogaClient().create(IRetrofit.class);
    }

}
