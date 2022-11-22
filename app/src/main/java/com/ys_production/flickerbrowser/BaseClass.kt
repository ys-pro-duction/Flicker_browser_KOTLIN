package com.ys_production.flickerbrowser

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

@SuppressLint("Registered")
open class BaseClass: AppCompatActivity() {
    companion object{
        val PHOTOTRANSFER = "PHOTOTRANSFER"
        val SEARCH_QUERY = "SEARCH_QUERY"
    }
    internal fun setToolBar(isSet: Boolean){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isSet)
    }
}