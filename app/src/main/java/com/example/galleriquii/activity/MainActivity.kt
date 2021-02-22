package com.example.galleriquii.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galleriquii.adapter.GalleryImageAdapter
import com.example.galleriquii.model.GalleryImageModel
import com.example.galleriquii.viewmodel.GalleryImagesViewModel
import com.example.galleriquii.R
import com.example.galleriquii.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private var data: ArrayList<GalleryImageModel> = ArrayList()
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var mainActivityViewModel: GalleryImagesViewModel
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        val mAdapter = GalleryImageAdapter(data, this)

        mainActivityViewModel = ViewModelProvider(this).get(GalleryImagesViewModel::class.java)

        mainActivityViewModel.galleryImagesList
                .observe(this, Observer { galleryImageModelList ->
                    mAdapter.clear()
                    galleryImageModelList.forEach {
                        data.add(it)
                    }
                    mAdapter.notifyDataSetChanged()
                })

        val mRecyclerView = mainActivityBinding.recylcerViewMainActivity
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        mRecyclerView.setHasFixedSize(true) // Helps improve performance
        mRecyclerView.adapter = mAdapter

        mainActivityBinding.textInputEditTextMainActivity.doOnTextChanged { text, start, before, count ->
            if (!isSearching && text != null && text.isNotEmpty()) {
                isSearching = true
                Handler().postDelayed({
                    mainActivityViewModel.getGalleryImageList(text.toString())
                    isSearching = false
                }, 500)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val TAG = MainActivity::class.simpleName
    }
}