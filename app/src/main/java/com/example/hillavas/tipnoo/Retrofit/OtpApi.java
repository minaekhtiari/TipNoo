package com.example.hillavas.tipnoo.Retrofit;

import com.example.hillavas.tipnoo.Models.ResultJson;
import com.example.hillavas.tipnoo.Models.SubscribeConfirmModel;
import com.example.hillavas.tipnoo.Models.SubscribeModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface OtpApi {


    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("OTP/SubScribeRequest")
    Call<ResultJson> subscribe(@Body SubscribeModel subscribeModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/OTP/SubscribeConfirm")
    Call<ResultJson> subscribeConfirm(@Body SubscribeConfirmModel subscribeConfirmModel);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/Otp/SubscribeConfirmViaCode")
    Call<ResultJson> subscribeConfirmViaCode(@Body SubscribeConfirmModel subscribeConfirmModel);



}
