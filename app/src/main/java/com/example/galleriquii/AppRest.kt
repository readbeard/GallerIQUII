package com.example.galleriquii

import android.util.Log
import com.google.gson.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


object AppRest {
    private const val baseUrl = "https://www.reddit.com/"
    private var gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
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