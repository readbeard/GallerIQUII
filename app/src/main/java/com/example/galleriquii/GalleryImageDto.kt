package com.example.galleriquii

import com.google.gson.annotations.SerializedName

class GalleryImageDto {
    @SerializedName("thumbnail")
    var thumbnailUrl: String? = null
    @SerializedName("url")
    var url: String? = null
}