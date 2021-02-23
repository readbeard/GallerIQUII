package com.example.galleriquii.activity

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleriquii.adapter.GalleryImageAdapter
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.viewmodel.GalleryImagesViewModel
import com.example.galleriquii.R
import com.example.galleriquii.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var mGalleryImageAdapter: GalleryImageAdapter
    private var data: ArrayList<GalleryImageModel> = ArrayList()
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var mainActivityViewModel: GalleryImagesViewModel
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        mGalleryImageAdapter = GalleryImageAdapter(data, this)

        mainActivityViewModel = ViewModelProvider(this).get(GalleryImagesViewModel::class.java)

        initializeGalleryImagesRecyclerView()
        initializeSearchInputEditText()
        observeForImagesListChanges()
    }

    private fun initializeGalleryImagesRecyclerView() {
        val mRecyclerView = mainActivityBinding.recylcerViewMainActivity
        mRecyclerView.layoutManager = GridLayoutManager(this, RECYCLER_VIEW_SPAN_COUNT)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = mGalleryImageAdapter
    }

    private fun initializeSearchInputEditText() {
        mainActivityBinding.textInputEditTextMainActivity.doOnTextChanged { text, _, _, _ ->
            if (!isSearching && text != null && text.isNotEmpty()) {
                isSearching = true
                Handler().postDelayed({
                    mainActivityViewModel.getGalleryImageList(text.toString())
                    isSearching = false
                }, SEARCH_INTERVAL_MILLIS)
            }
        }
    }

    private fun observeForImagesListChanges() {
        mainActivityViewModel.galleryImagesList
            .observe(this, { galleryImageModelList ->
                mGalleryImageAdapter.clear()
                galleryImageModelList.forEach {
                    data.add(it)
                }
                mGalleryImageAdapter.notifyDataSetChanged()
            })
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val RECYCLER_VIEW_SPAN_COUNT = 3
        private const val SEARCH_INTERVAL_MILLIS = 500L
    }
}