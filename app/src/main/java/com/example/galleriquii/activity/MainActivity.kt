package com.example.galleriquii.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var galleryImagesRecyclerView: RecyclerView
    private lateinit var noImageFoundTextView: TextView
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        mainActivityViewModel = ViewModelProvider(this).get(GalleryImagesViewModel::class.java)

        loadingProgressBar = mainActivityBinding.progressBarMainActivity
        noImageFoundTextView = mainActivityBinding.textViewMainActivityNoImageFound

        initializeGalleryImagesRecyclerView()
        initializeSearchInputEditText()
        observeForImagesListChanges()
    }

    private fun initializeGalleryImagesRecyclerView() {
        galleryImagesRecyclerView = mainActivityBinding.recylcerViewMainActivity
        galleryImagesRecyclerView.layoutManager = GridLayoutManager(this, RECYCLER_VIEW_SPAN_COUNT)
        galleryImagesRecyclerView.setHasFixedSize(true)
        mGalleryImageAdapter = GalleryImageAdapter(data, this)
        galleryImagesRecyclerView.adapter = mGalleryImageAdapter
    }

    private fun initializeSearchInputEditText() {
        mainActivityBinding.textInputEditTextMainActivity.doOnTextChanged { text, _, _, _ ->
            if (!isSearching && text != null && text.isNotEmpty()) {
                shouldShowLoadingSpinner(true)
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
                shouldShowLoadingSpinner(false)
                shouldShowEmptyImageListText(galleryImageModelList.isEmpty())
                mGalleryImageAdapter.clear()
                galleryImageModelList.forEach {
                    data.add(it)
                }
                mGalleryImageAdapter.notifyDataSetChanged()
            })
    }

    private fun shouldShowLoadingSpinner(show: Boolean) {
        shouldShowEmptyImageListText(false)
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            galleryImagesRecyclerView.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            galleryImagesRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun shouldShowEmptyImageListText(show: Boolean) {
        if(show) {
            noImageFoundTextView.visibility = View.VISIBLE
        } else {
            noImageFoundTextView.visibility = View.GONE
        }
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val RECYCLER_VIEW_SPAN_COUNT = 3
        private const val SEARCH_INTERVAL_MILLIS = 500L
    }
}