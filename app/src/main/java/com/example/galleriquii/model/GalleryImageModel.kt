package com.example.galleriquii.model

import android.os.Parcel
import android.os.Parcelable

class GalleryImageModel(var name: String? = "",
                        var url: String? = "",
                        var thumbnailUrl: String? = "",
                        var authorFullname: String? = "",
                        var title: String? = "",
                        var createdUtc: String? = ""): Parcelable {

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        url = parcel.readString().toString()
        thumbnailUrl = parcel.readString().toString()
        authorFullname = parcel.readString().toString()
        title = parcel.readString().toString()
        createdUtc = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(authorFullname)
        parcel.writeString(title)
        parcel.writeString(createdUtc)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "GalleryImageModel(name=$name, url=$url, thumbnailUrl=$thumbnailUrl, authorFullname=$authorFullname, title=$title, createdUtc=$createdUtc)"
    }

    companion object CREATOR : Parcelable.Creator<GalleryImageModel> {
        override fun createFromParcel(parcel: Parcel): GalleryImageModel {
            return GalleryImageModel(parcel)
        }

        override fun newArray(size: Int): Array<GalleryImageModel?> {
            return Array(20) { GalleryImageModel() }
        }

    }

}