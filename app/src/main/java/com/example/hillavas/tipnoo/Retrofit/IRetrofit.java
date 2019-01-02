package com.example.hillavas.tipnoo.Retrofit;


import com.example.hillavas.tipnoo.Models.MemberSignToken;
import com.example.hillavas.tipnoo.Models.getMemberSignTokenBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IRetrofit {
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/mobileApi/Member/SignUp")
    Call<MemberSignToken>getMemberSignToken(@Body getMemberSignTokenBody getMemberSignTokenBody);




}
