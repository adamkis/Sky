package com.example.sky.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sky.R
import com.example.sky.model.Photo
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import android.widget.TextView


class PhotoDetailFragment : Fragment() {

    private var photo: Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            photo = arguments!!.getParcelable(ARG_PHOTO)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillInfo(view, photo)
    }

    private fun fillInfo(view: View, photo: Photo?){
        if (photo == null) return
        recents_photo_id.text = photo?.id
        val row1 = view.findViewById<View>(R.id.info_row_1)
        val row2 = view.findViewById<View>(R.id.info_row_2)
        val row3 = view.findViewById<View>(R.id.info_row_3)
        val row4 = view.findViewById<View>(R.id.info_row_4)
        val row5 = view.findViewById<View>(R.id.info_row_5)
        val row6 = view.findViewById<View>(R.id.info_row_6)
        row1.findViewById<TextView>(R.id.info_title).text = photo!!::farm.name
        row1.findViewById<TextView>(R.id.info_value).text = photo?.farm
        row2.findViewById<TextView>(R.id.info_title).text = photo!!::owner.name
        row2.findViewById<TextView>(R.id.info_value).text = photo?.owner
        row3.findViewById<TextView>(R.id.info_title).text = photo!!::server.name
        row3.findViewById<TextView>(R.id.info_value).text = photo?.server
        row4.findViewById<TextView>(R.id.info_title).text = photo!!::isfriend.name
        row4.findViewById<TextView>(R.id.info_value).text = photo?.isfriend.toString()
        row5.findViewById<TextView>(R.id.info_title).text = photo!!::isfamily.name
        row5.findViewById<TextView>(R.id.info_value).text = photo?.isfamily.toString()
        row6.findViewById<TextView>(R.id.info_title).text = photo!!::ispublic.name
        row6.findViewById<TextView>(R.id.info_value).text = photo?.ispublic.toString()
    }

    companion object {

        private val ARG_PHOTO = "ARG_PHOTO"

        fun newInstance(photo: Photo): PhotoDetailFragment {
            val fragment = PhotoDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_PHOTO, photo)
            fragment.arguments = args
            return fragment
        }

    }

}