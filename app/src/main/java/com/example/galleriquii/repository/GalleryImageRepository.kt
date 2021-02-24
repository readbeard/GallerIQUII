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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object GalleryImageRepository {
    private val TAG = GalleryImageRepository::class.simpleName
    private var galleryIQUIIwebService: GalleryApi = AppRest.apiInstance

    fun getImagesForKeyword(keyword: String, liveData: MutableLiveData<List<GalleryImageModel>>) {
        val call = galleryIQUIIwebService.retrieveImages(keyword)
        val galleryImagesList = ArrayList<GalleryImageModel>()
        call.enqueue(object : Callback<RedditResponseDto> {
            override fun onResponse(call: Call<RedditResponseDto>, response: Response<RedditResponseDto>) {
                if (response.body() == null) {
                    liveData.value = ArrayList()
                    return
                }
                val redditResponseDto = response.body() as RedditResponseDto

                redditResponseDto.data?.children?.forEach {
                    if (it.childData?.isVideo == false) {
                        val childData = it.childData
                        val childThumbnailUrl = childData?.thumbnail
                        val childUrl = childData?.url
                        val dateTimestamp = if (childData?.createdUtc == null) 0 else childData.createdUtc!! * 1000L
                        if(URLUtil.isValidUrl(childThumbnailUrl) && URLUtil.isValidUrl(childUrl)) {
                            val galleryImageModel = GalleryImageModel().apply {
                                name = it.childData?.name
                                url = childUrl
                                thumbnailUrl = childThumbnailUrl
                                authorFullname = childData?.authorFullname
                                title = childData?.title
                                createdUtc = SimpleDateFormat("dd MM yyyy HH:mm", Locale.getDefault()).format(Date(dateTimestamp))
                            }

                            galleryImagesList.add(galleryImageModel)
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