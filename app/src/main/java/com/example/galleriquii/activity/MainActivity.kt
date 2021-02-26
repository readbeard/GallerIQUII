package com.example.galleriquii.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galleriquii.NetworkStatusMonitor
import com.example.galleriquii.adapter.GalleryImageAdapter
import com.example.galleriquii.databinding.ActivityMainBinding
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.viewmodel.GalleryImagesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), NetworkStatusMonitor.NetworkStatusListener {
    private lateinit var mGalleryImageAdapter: GalleryImageAdapter
    private var data: ArrayList<GalleryImageModel> = ArrayList()
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var mainActivityViewModel: GalleryImagesViewModel
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var galleryImagesRecyclerView: RecyclerView
    private lateinit var noImageFoundTextView: TextView
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(mainActivityBinding.toolbar)

        mainActivityViewModel = ViewModelProvider(this).get(GalleryImagesViewModel::class.java)

        loadingProgressBar = mainActivityBinding.progressBarMainActivity
        noImageFoundTextView = mainActivityBinding.textViewMainActivityNoImageFound

        initializeGalleryImagesRecyclerView()
        initializeSearchInputEditText()
        observeForImagesListChanges()

        val stateMonitor = NetworkStatusMonitor()
        stateMonitor.enable(this)
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
            if (text != null && text.isNotEmpty()) {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    text.let {
                        delay(SEARCH_INTERVAL_MILLIS)
                        if (it.isNotEmpty()) {
                            shouldShowLoadingSpinner(true)
                            mainActivityViewModel.getGalleryImageList(text.toString())
                        }
                    }
                }

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
        if (show) {
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

    override fun onResume() {
        NetworkStatusMonitor.networkStatusListener = this
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        Snackbar.make(
            mainActivityBinding.constraintLayoutMainActivityContainer,
            "You are ${if (isConnected) "online" else "offline"}",
            Snackbar.LENGTH_LONG
        ).show()
    }
}