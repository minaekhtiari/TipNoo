package com.example.pc_ekhtiari.tipnoo.Retrofit;



import com.example.pc_ekhtiari.tipnoo.Models.ContentResult;
import com.example.pc_ekhtiari.tipnoo.Models.TabResults;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by MinaPC on 7/20/2017.
 */

public interface FileApi {


    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Category/GetAll")
    Call<TabResults> getPerfumeTabs(@Query("parentId") int parentId);

    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Content/GetOrderedContentsWithCategoryId")
    Call<ContentResult>getContent(@Query("Token") String Token, @Query("CategoryId") int CategoryId, @Query("PageNumber") int PageNumber,
                                  @Query("RowCount") int RowCount, @Query("QueryType") String QueryType);
}




