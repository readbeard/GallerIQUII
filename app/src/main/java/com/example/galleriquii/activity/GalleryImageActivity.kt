package com.example.galleriquii.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.R
import com.example.galleriquii.adapter.SectionsPagerAdapter


class GalleryImageActivity : AppCompatActivity() {
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var data: ArrayList<GalleryImageModel> = ArrayList()
    private var pos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_image)

        data = intent.getParcelableArrayListExtra("data");
        pos = intent.getIntExtra("pos", 0);

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, data)

        val mViewPager = findViewById<View>(R.id.container) as ViewPager
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