package com.example.hillavas.tipnoo.Retrofit;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinaPC on 7/20/2017.
 */

public class RetroClient {
    private static final String ROOT_URL = "http://79.175.138.95:1010/";

     public static String FILE_URL="http://79.175.138.77:7091/file/getfile?FileType=%s&fileid=%s";

    public RetroClient() {

    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static FileApi getApiService() {
        return getRetroClient().create(FileApi.class);
    }

}
