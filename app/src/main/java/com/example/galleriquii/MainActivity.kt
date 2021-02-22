package com.example.galleriquii

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    var data: ArrayList<GalleryImageModel> = ArrayList()
    var IMGS = arrayOf<String>(
        "https://cdn.maikoapp.com/3d4b/4qhf5/180h.png",
        "https://cdn.maikoapp.com/3d4b/4qtwb/200x150.png",
        "https://cdn.maikoapp.com/3d4b/4qgko/p130.jpg",
        "https://cdn.maikoapp.com/3d4b/4r2dg/180h.jpg",
        "https://cdn.maikoapp.com/3d4b/4qy9k/180w.jpg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        for (i in IMGS.indices) {
            val imageModel = GalleryImageModel("Image $i", IMGS[i])
            data.add(imageModel)
        }

        val mRecyclerView = findViewById<View>(R.id.list) as RecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        mRecyclerView.setHasFixedSize(true) // Helps improve performance


        val mAdapter = GalleryImageAdapter(data,  this)
        mRecyclerView.adapter = mAdapter

        GalleryImageRepository.getImagesForKeyword("dog")
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