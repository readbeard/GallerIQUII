package com.example.galleriquii.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.R
import com.example.galleriquii.adapter.SectionsPagerAdapter
import com.example.galleriquii.databinding.ActivityGalleryImageBinding
import com.example.galleriquii.fragment.GalleryImageInfoFragment


class GalleryImageActivity : AppCompatActivity() {
    private lateinit var galleryItemViewPager: ViewPager
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var data: ArrayList<GalleryImageModel> = ArrayList()
    private var pos = 0
    private lateinit var binding: ActivityGalleryImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        data = intent.getParcelableArrayListExtra("data")
        pos = intent.getIntExtra("pos", 0)
        title = data[pos].name

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, data)

        galleryItemViewPager = binding.viewPager
        galleryItemViewPager.adapter = mSectionsPagerAdapter
        galleryItemViewPager.currentItem = pos

        galleryItemViewPager.addOnPageChangeListener(object : OnPageChangeListener {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_gallery_image, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (isInfoFragmentVisible()) {
            galleryItemViewPager.visibility = View.VISIBLE
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info -> {
                if (isInfoFragmentVisible()) {
                    return super.onOptionsItemSelected(item)
                }
                val currentGalleryItem = data[pos]
                val infoFragment = GalleryImageInfoFragment.newInstance(
                    currentGalleryItem.name,
                    currentGalleryItem.authorFullname,
                    currentGalleryItem.title,
                    currentGalleryItem.createdUtc
                )
                galleryItemViewPager.visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.container, infoFragment)
                    .addToBackStack(null).commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isInfoFragmentVisible(): Boolean {
        return supportFragmentManager.fragments.last() is GalleryImageInfoFragment
    }

    companion object {
        private val TAG = GalleryImageActivity::class.java.simpleName
    }
}