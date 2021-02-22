package com.example.galleriquii.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.repository.GalleryImageRepository

class GalleryImagesViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var imagesUrlList: LiveData<List<GalleryImageModel>>

    fun getGalleryImageList(keyword: String): LiveData<List<GalleryImageModel>> {
        if (!this::imagesUrlList.isInitialized) {
            imagesUrlList = GalleryImageRepository.getImagesForKeyword(keyword)
        }
        return imagesUrlList;
    }
}