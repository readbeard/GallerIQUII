package com.example.galleriquii.model

import android.os.Parcel
import android.os.Parcelable

class GalleryImageModel(var name: String = "", var url: String = "", var thumbnailUrl: String = ""): Parcelable {

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        url = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
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