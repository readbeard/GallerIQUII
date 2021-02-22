package com.example.galleriquii

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GalleryApi {
    @GET("/r/{keyword}/top.json")
    fun retrieveImages(@Path("keyword") keyword: String): Call<RedditResponseDto>
}
