package com.example.hillavas.tipnoo.Retrofit;



import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Models.ResultJsonMemberSignUp;
import com.example.hillavas.tipnoo.Models.TabResults;
import com.example.hillavas.tipnoo.Models.TagResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by MinaPC on 7/20/2017.
 */

public interface FileApi {


    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Category/GetAll")
    Call<TabResults> getAlleContent(@Query("parentId") int parentId);

    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Content/GetOrderedContentsWithCategoryId")
    Call<ContentResult>getContent(@Query("Token") String Token, @Query("CategoryId") int CategoryId, @Query("PageNumber") int PageNumber,
                                  @Query("RowCount") int RowCount, @Query("QueryType") String QueryType);

    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Tag/GetAllTag")
    Call<TagResults>getSearchTags();

    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Content/GetOrderedContentsWithTagIds")
    Call<ContentResult>getWithTagIdsContent(@Query("Token") String Token,@Query("CategoryId") int CategoryId, @Query("PageNumber") int PageNumber,
                                  @Query("RowCount") int RowCount, @Query("QueryType") String QueryType,@Query("TagIds") List<Integer> TagIds);

    @Headers("Token: 007b428d-b807-4ccd-a3a8-afdcc0f18d0b")
    @GET("Api/Content/GetFilterOrderedContents")
    Call<ContentResult>getWithTagIdsContent(@Query("Token") String Token,@Query("CategoryId") int CategoryId, @Query("PageNumber") int PageNumber,
                                            @Query("RowCount") int RowCount, @Query("QueryType") String QueryType);

    @POST("Member/SignUp")
    Call<ResultJsonMemberSignUp> memberSignUp(@Query("mobileNumber")  String appType );
}




