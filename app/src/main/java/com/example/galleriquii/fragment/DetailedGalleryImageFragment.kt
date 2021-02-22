package com.example.galleriquii.fragment

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.galleriquii.R


/**
 * A placeholder fragment containing a simple view.
 */
class DetailedGalleryImageFragment : Fragment() {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    var name: String? = null
    var url: String? = null
    var pos: Int? = null

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
        val rootView: View = inflater.inflate(R.layout.fragment_gallery_image, container, false)
        val imageView: ImageView = rootView.findViewById(R.id.detail_image) as ImageView
        val darkModeEnabled = context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)?.equals(Configuration.UI_MODE_NIGHT_YES)?: false

        val circularProgressDrawable = CircularProgressDrawable(requireActivity())
        circularProgressDrawable.strokeWidth = 10f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            if(darkModeEnabled) Color.WHITE else Color.BLACK, BlendModeCompat.SRC_ATOP
            )
        circularProgressDrawable.start()

        Glide.with(requireActivity())
            .load(url)
            .placeholder(circularProgressDrawable)
            .error(if (darkModeEnabled) R.drawable.ic_baseline_broken_image_24_white else R.drawable.ic_baseline_broken_image_24_black)
            .into(imageView)

        return rootView
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_IMG_TITLE = "image_title"
        private const val ARG_IMG_URL = "image_url"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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