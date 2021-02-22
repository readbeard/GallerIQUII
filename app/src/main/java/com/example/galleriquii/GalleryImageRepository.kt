package com.example.galleriquii

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GalleryImageRepository {
    private val TAG = GalleryImageRepository::class.simpleName
    private var galleryIQUIIwebService: GalleryApi = AppRest.apiInstance //TODO: move this to viewmodel

    fun getImagesForKeyword(keyword: String) {
        val call = galleryIQUIIwebService.retrieveImages(keyword)
        call.enqueue(object : Callback<RedditResponseDto> {
            override fun onResponse(call: Call<RedditResponseDto>, response: Response<RedditResponseDto>) {
                val redditResponseDto = response.body() as RedditResponseDto

                for (child in redditResponseDto.data?.children.orEmpty()) {
                    if (child.childData?.isVideo == true) continue
                    Log.e(TAG, child.childData?.url?: "")
                }
            }
            override fun onFailure(call: Call<RedditResponseDto>, t: Throwable) {
                Log.e(TAG, t.message?: "call failed with null throwable")
            }
        })
    }
}