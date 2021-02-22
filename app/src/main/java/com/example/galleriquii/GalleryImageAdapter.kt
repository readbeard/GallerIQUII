package com.example.galleriquii

import android.R.attr.data
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.galleriquii.databinding.GalleryImageItemBinding


class GalleryImageAdapter(
    private val items: ArrayList<GalleryImageModel>,
    private val context: Context
) : RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageViewHolder {
        val binding =
            GalleryImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GalleryImageViewHolder(binding, null)
    }

    override fun onBindViewHolder(holderGalleryImage: GalleryImageViewHolder, position: Int) {
        holderGalleryImage.setOnClickListener {
            val intent = Intent(context, GalleryImageActivity::class.java)
            intent.putParcelableArrayListExtra("data", items)
            intent.putExtra("pos", position)
            context.startActivity(intent)
        }
        holderGalleryImage.bind(items[position], context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class GalleryImageViewHolder(
        private val binding: GalleryImageItemBinding,
        private var onClickListener: ((View) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(result: GalleryImageModel, context: Context) {
            Glide.with(context).load(result.url)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((binding.itemImg))

            itemView.setOnClickListener(onClickListener)
        }

        fun setOnClickListener(listener: ((View) -> Unit)?) {
            this.onClickListener = listener
        }
    }
}
