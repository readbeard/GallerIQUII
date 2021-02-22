package com.example.galleriquii

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class GalleryImagesViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var imagesUrlList: LiveData<List<String>>

    fun getUrlList(keyword: String): LiveData<List<String>> {
        if (!this::imagesUrlList.isInitialized) {
            imagesUrlList = GalleryImageRepository.getImagesForKeyword(keyword)
        }
        return imagesUrlList;
    }
}