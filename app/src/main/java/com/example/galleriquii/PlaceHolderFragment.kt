package com.example.galleriquii

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {
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
        Glide.with(requireActivity()).load(url).thumbnail(0.1f).into(imageView)
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
        fun newInstance(sectionNumber: Int, name: String?, url: String?): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            args.putString(ARG_IMG_TITLE, name)
            args.putString(ARG_IMG_URL, url)
            fragment.arguments = args
            return fragment
        }
    }
}