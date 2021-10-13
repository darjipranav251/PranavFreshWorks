package com.app.pranavfreshworks.network

import com.app.pranavfreshworks.model.GiphyResponseModel
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("v1/gifs/trending")
    fun trendingList(@QueryMap body: HashMap<String, String>): Call<GiphyResponseModel>

    @GET("v1/gifs/search")
    fun search(@QueryMap body: HashMap<String, String>): Call<GiphyResponseModel>

}