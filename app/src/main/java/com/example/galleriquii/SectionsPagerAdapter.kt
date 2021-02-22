package com.example.galleriquii

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, data: ArrayList<GalleryImageModel>) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var data: ArrayList<GalleryImageModel> = ArrayList()
    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(
            position,
            data[position].name,
            data[position].url
        )
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].name
    }

    init {
        this.data = data
    }
}