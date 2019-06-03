package com.app.goalwise.api;


import com.app.goalwise.models.FundsList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("search")
    Call<List<FundsList>> fundsList(@Field("keyword") String keyword, @Header("x-api-key") String apiKey);




}

