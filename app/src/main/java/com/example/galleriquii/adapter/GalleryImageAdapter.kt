package com.example.galleriquii.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.galleriquii.R
import com.example.galleriquii.activity.GalleryImageActivity
import com.example.galleriquii.databinding.GalleryImageItemBinding
import com.example.galleriquii.model.GalleryImageModel


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

    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    class GalleryImageViewHolder(
        private val binding: GalleryImageItemBinding,
        private var onClickListener: ((View) -> Unit)?,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val TAG = GalleryImageViewHolder::class.simpleName

        @SuppressLint("SetTextI18n")
        fun bind(result: GalleryImageModel, context: Context) {
            val darkModeEnabled =
                context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                    ?.equals(Configuration.UI_MODE_NIGHT_YES) ?: false
            Glide.with(context).load(result.thumbnailUrl)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(if (darkModeEnabled) R.drawable.ic_baseline_broken_image_24_white else R.drawable.ic_baseline_broken_image_24_black)
                .into((binding.itemImg))

            itemView.setOnClickListener(onClickListener)
        }

        fun setOnClickListener(listener: ((View) -> Unit)?) {
            this.onClickListener = listener
        }
    }

    companion object {
        private val TAG = GalleryImageAdapter::class.simpleName
    }
}
