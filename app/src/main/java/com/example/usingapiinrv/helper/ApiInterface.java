package com.example.usingapiinrv.helper;

import com.example.usingapiinrv.model.PlayerData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("json_parsing.php")
    Call<PlayerData> loadData();

}
