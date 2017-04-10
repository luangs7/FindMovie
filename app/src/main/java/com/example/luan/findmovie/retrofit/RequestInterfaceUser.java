package com.example.luan.findmovie.retrofit;



import com.example.luan.findmovie.model.request.MainRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Luan on 07/04/2017 
 */
public interface RequestInterfaceUser {

    //String api_key = "b5127d1016c968d30de8d0d6cc725a73";

    
    @GET("popular")
    Call<MainRequest> getFilmesPopular(@Query("api_key") String api_key, @Query("page") String page);

    @GET("top_rated")
    Call<MainRequest> getFilmesVotados(@Query("api_key") String api_key, @Query("page") String page);


}
