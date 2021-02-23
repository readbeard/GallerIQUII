package com.example.galleriquii.rest

import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppRest {
    private const val baseUrl = "https://www.reddit.com/"
    private const val dateFormat = "yyyy-MM-dd HH:mm:ss"
    private var gson: Gson = GsonBuilder()
        .setDateFormat(dateFormat)
        .create()

    var okHttpClient: OkHttpClient = OkHttpClient().newBuilder().build()

    var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    var apiInstance: GalleryApi = retrofit.create(GalleryApi::class.java)

}