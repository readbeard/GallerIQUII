package com.example.galleriquii.repository

import android.util.Log
import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import com.example.galleriquii.dto.ChildrenData
import com.example.galleriquii.dto.RedditResponseDto
import com.example.galleriquii.model.GalleryImageModel
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
    private const val dateFormat = "dd MM yyyy HH:mm"
    private var galleryIQUIIWebService: GalleryApi = AppRest.apiInstance

    fun getImagesForKeyword(keyword: String, liveData: MutableLiveData<List<GalleryImageModel>>) {
        val call = galleryIQUIIWebService.retrieveImages(keyword)
        call.enqueue(object : Callback<RedditResponseDto> {
            override fun onResponse(
                call: Call<RedditResponseDto>,
                response: Response<RedditResponseDto>
            ) {
                val galleryImagesList = ArrayList<GalleryImageModel>()
                val redditResponseDto = response.body() ?: RedditResponseDto()
                redditResponseDto.data?.children?.forEach {
                    if (it.childData != null && it.childData?.isVideo == false) {
                        fillGalleryImageList(it.childData!!, galleryImagesList)
                    }
                }
                liveData.value = galleryImagesList
            }

            override fun onFailure(call: Call<RedditResponseDto>, t: Throwable) {
                Log.e(TAG, t.message ?: "call failed with null throwable")
            }
        })

    }

    private fun fillGalleryImageList(
        childData: ChildrenData,
        galleryImagesList: ArrayList<GalleryImageModel>
    ) {
        val childThumbnailUrl = childData.thumbnail
        val childUrl = childData.url
        val dateTimestamp = if (childData.createdUtc == null) 0 else childData.createdUtc!! * 1000L
        if (URLUtil.isValidUrl(childThumbnailUrl) && URLUtil.isValidUrl(childUrl)) {
            val galleryImageModel = GalleryImageModel().apply {
                name = childData.name
                url = childUrl
                thumbnailUrl = childThumbnailUrl
                authorFullname = childData.authorFullname
                title = childData.title
                createdUtc = SimpleDateFormat(dateFormat, Locale.getDefault()).format(
                    Date(dateTimestamp)
                )
            }
            galleryImagesList.add(galleryImageModel)
        }
    }
}