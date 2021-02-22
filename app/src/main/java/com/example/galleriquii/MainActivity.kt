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

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}