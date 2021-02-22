package com.example.galleriquii.rest

import com.example.galleriquii.RedditResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GalleryApi {
    @GET("/r/{keyword}/top.json")
    fun retrieveImages(@Path("keyword") keyword: String): Call<RedditResponseDto>
}
