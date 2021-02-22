package com.example.galleriquii

import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GalleryImageRepository {
    private val TAG = GalleryImageRepository::class.simpleName
    private var urlLiveData: MutableLiveData<List<String>> = MutableLiveData()
    private var galleryIQUIIwebService: GalleryApi = AppRest.apiInstance //TODO: move this to viewmodel

    fun getImagesForKeyword(keyword: String): LiveData<List<String>> {
        val call = galleryIQUIIwebService.retrieveImages(keyword)
        val urlList = ArrayList<String>()
        call.enqueue(object : Callback<RedditResponseDto> {
            override fun onResponse(call: Call<RedditResponseDto>, response: Response<RedditResponseDto>) {
                val redditResponseDto = response.body() as RedditResponseDto
                redditResponseDto.data?.children?.forEach {
                    if (it.childData?.url != null && it.childData?.isVideo == false) {
                        val thumbnailUrl = it.childData?.thumbnail!!
                        if(URLUtil.isValidUrl(thumbnailUrl)) {
                            urlList.add(thumbnailUrl)
                        }

                    }
                }
                urlLiveData.value = urlList
            }
            override fun onFailure(call: Call<RedditResponseDto>, t: Throwable) {
                Log.e(TAG, t.message?: "call failed with null throwable")
            }
        })

        return urlLiveData
    }
}