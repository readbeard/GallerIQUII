package com.example.galleriquii.fragment

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.galleriquii.R
import com.example.galleriquii.utils.TouchImageView
import com.example.galleriquii.databinding.FragmentGalleryImageBinding

class DetailedGalleryImageFragment : Fragment() {
    var name: String? = null
    var url: String? = null
    private var pos: Int? = null
    private lateinit var binding: FragmentGalleryImageBinding

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        pos = args?.getInt(ARG_SECTION_NUMBER)
        name = args?.getString(ARG_IMG_TITLE)
        url = args?.getString(ARG_IMG_URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryImageBinding.inflate(layoutInflater, container, false)

        val imageView: TouchImageView = binding.detailImage
        loadPicIntoImageView(imageView)

        return binding.root
    }

    private fun loadPicIntoImageView(imageView: TouchImageView) {
        val darkModeEnabled =
            context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                ?.equals(Configuration.UI_MODE_NIGHT_YES) ?: false
        val circularProgressDrawable = initializeCircularProgressDrawable(darkModeEnabled)
        Glide.with(requireActivity())
            .load(url)
            .placeholder(circularProgressDrawable)
            .error(if (darkModeEnabled) R.drawable.ic_baseline_broken_image_24_white else R.drawable.ic_baseline_broken_image_24_black)
            .into(imageView)
    }

    private fun initializeCircularProgressDrawable(darkModeEnabled: Boolean): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(requireActivity())
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                if (darkModeEnabled) Color.WHITE else Color.BLACK, BlendModeCompat.SRC_ATOP
            )
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_IMG_TITLE = "image_title"
        private const val ARG_IMG_URL = "image_url"

        fun newInstance(sectionNumber: Int, name: String?, url: String?): DetailedGalleryImageFragment {
            val fragment = DetailedGalleryImageFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            args.putString(ARG_IMG_TITLE, name)
            args.putString(ARG_IMG_URL, url)
            fragment.arguments = args
            return fragment
        }
    }
}