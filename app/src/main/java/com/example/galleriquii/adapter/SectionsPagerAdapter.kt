package com.example.galleriquii.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.fragment.DetailedGalleryImageFragment

class SectionsPagerAdapter(fm: FragmentManager, data: ArrayList<GalleryImageModel>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var data: ArrayList<GalleryImageModel> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return DetailedGalleryImageFragment.newInstance(
            position,
            data[position].name,
            data[position].url
        )
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].name
    }

    init {
        this.data = data
    }
}