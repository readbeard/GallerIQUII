package com.example.galleriquii.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.repository.GalleryImageRepository

class GalleryImagesViewModel(application: Application) : AndroidViewModel(application) {
    var galleryImagesList: MutableLiveData<List<GalleryImageModel>> = MutableLiveData()

    fun getGalleryImageList(keyword: String) {
        GalleryImageRepository.getImagesForKeyword(keyword, galleryImagesList)
    }
}