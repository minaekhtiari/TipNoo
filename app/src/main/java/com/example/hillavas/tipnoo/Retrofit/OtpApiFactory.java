package com.example.hillavas.tipnoo.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OtpApiFactory {


    private static final String URL = "http://79.175.138.118/";

    private static Retrofit retrofit;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            Retrofit.Builder reBuilder = new Retrofit.Builder();
            reBuilder
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = reBuilder.build();
        }
        return retrofit;
    }

    public static OtpApi getOtpClient() {
        OtpApi retroClient = OtpApiFactory.getRetrofit().create(OtpApi.class);

        return retroClient;
    }
}
