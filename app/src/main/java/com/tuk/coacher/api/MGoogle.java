package com.tuk.coacher.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MGoogle {

    @GET
    Call<String> getDataFromGoogleApi(@Url String url);
}
