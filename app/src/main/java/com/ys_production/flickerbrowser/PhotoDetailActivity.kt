package com.ys_production.flickerbrowser

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.squareup.picasso.Picasso
import java.util.concurrent.Executors

class PhotoDetailActivity : BaseClass() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        setToolBar(true)
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate: ")
        }
        val photoObject: FlickerDataModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable(PHOTOTRANSFER,FlickerDataModel::class.java)
        } else {
            intent.extras?.getParcelable(PHOTOTRANSFER)
        }
        val titleText = findViewById<TextView>(R.id.detail_title)
        val authorText = findViewById<TextView>(R.id.detail_author)
        val tagsText = findViewById<TextView>(R.id.detail_tags)
        val publicshedText = findViewById<TextView>(R.id.detail_published)
        val linkText = findViewById<TextView>(R.id.detail_link)
        val imageview = findViewById<ImageView>(R.id.detail_imageView)

        photoObject?.let{
            titleText.text = it.title

            authorText.text = resources.getString(R.string.detail_author_txt,it.author)
            tagsText.text = resources.getString(R.string.detail_tags_txt,it.tags)
            publicshedText.text = resources.getString(R.string.detail_date_txt,it.published)
            linkText.text = resources.getString(R.string.detail_link_txt,it.link)
            Picasso.get().load(it.imglink).into(imageview)
            linkText.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(photoObject.link)))
            }
        }
        titleText.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        authorText.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
    companion object {
        private const val TAG = "PhotoDetailActivity"
    }
}