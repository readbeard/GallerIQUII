package com.example.galleriquii.repository

import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.dto.RedditResponseDto
import com.example.galleriquii.rest.AppRest
import com.example.galleriquii.rest.GalleryApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GalleryImageRepository {
    private val TAG = GalleryImageRepository::class.simpleName
    private var galleryIQUIIwebService: GalleryApi = AppRest.apiInstance

    fun getImagesForKeyword(keyword: String, liveData: MutableLiveData<List<GalleryImageModel>>) {
        val call = galleryIQUIIwebService.retrieveImages(keyword)
        val galleryImagesList = ArrayList<GalleryImageModel>()
        call.enqueue(object : Callback<RedditResponseDto> {
            override fun onResponse(call: Call<RedditResponseDto>, response: Response<RedditResponseDto>) {
                if (response.body() == null) {
                    return
                }
                val redditResponseDto = response.body() as RedditResponseDto
                redditResponseDto.data?.children?.forEach {
                    if (it.childData?.isVideo == false) {
                        val thumbnailUrl = it.childData?.thumbnail
                        val url = it.childData?.url
                        if(URLUtil.isValidUrl(thumbnailUrl) && URLUtil.isValidUrl(url)) {
                            galleryImagesList.add(GalleryImageModel(it.childData?.name?: "", url!!, thumbnailUrl!!))
                        }
                    }
                }
                liveData.value = galleryImagesList
            }
            override fun onFailure(call: Call<RedditResponseDto>, t: Throwable) {
                Log.e(TAG, t.message?: "call failed with null throwable")
            }
        })

    }
}