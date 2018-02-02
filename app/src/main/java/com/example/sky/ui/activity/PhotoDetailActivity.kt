package com.example.sky.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.sky.R
import com.example.sky.model.Photo
import kotlinx.android.synthetic.main.activity_photo_detail.*
import com.example.sky.helper.FilePersistenceHelper
import com.example.sky.ui.fragment.PhotoDetailFragment


class PhotoDetailActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        val photo: Photo = intent.getParcelableExtra(Photo.TAG)
        val bitmap: Bitmap? = FilePersistenceHelper.loadBitmapFromFile(this)

        var collapsingToolbarLayout: CollapsingToolbarLayout? = null
        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = if (photo.title.isNullOrBlank()) getString(R.string.photo_detail) else photo.title
        header_image.setImageBitmap(bitmap)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PhotoDetailFragment.newInstance(photo)).commit()

    }

    companion object {
        fun getStartIntent(context: Context, photo: Photo): Intent {
            return Intent(context, PhotoDetailActivity::class.java)
                    .apply { putExtra(Photo.TAG, photo) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}