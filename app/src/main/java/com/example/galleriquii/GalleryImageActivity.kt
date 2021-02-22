package com.example.galleriquii

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener


class GalleryImageActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    var data: ArrayList<GalleryImageModel> = ArrayList()
    var pos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_image)


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        data = intent.getParcelableArrayListExtra("data");
        pos = intent.getIntExtra("pos", 0);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, data)
        // Set up the ViewPager with the sections adapter.
        // Set up the ViewPager with the sections adapter.
        val mViewPager = findViewById<View>(R.id.container) as ViewPager
        //mViewPager.setPageTransformer(true, DepthPageTransformer())

        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.currentItem = pos

        mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                title = data[position].name
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}