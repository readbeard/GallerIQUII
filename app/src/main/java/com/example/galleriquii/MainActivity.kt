package com.example.galleriquii

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    var data: ArrayList<GalleryImageModel> = ArrayList()
    lateinit var mainActivityViewModel: GalleryImagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val mAdapter = GalleryImageAdapter(data,  this)

        mainActivityViewModel = ViewModelProvider(this).get(GalleryImagesViewModel::class.java)

        mainActivityViewModel.getUrlList("dog").observe(this, Observer {
            for (i in it.indices) {
                val imageModel = GalleryImageModel("Image $i", it[i])
                data.add(imageModel)
            }
            mAdapter.notifyDataSetChanged()
        })

        val mRecyclerView = findViewById<View>(R.id.list) as RecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        mRecyclerView.setHasFixedSize(true) // Helps improve performance
        mRecyclerView.adapter = mAdapter


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
}