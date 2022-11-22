package com.ys_production.flickerbrowser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

class MainActivity : BaseClass(), FlickerJsonData.FlickerJsonDataInter,
    RecyclerClickListener.Clickevent {
    private val recyclerAdepter = RecyclerAdepter(ArrayList())
    private var lastQueary = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolBar(false)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdepter
        recyclerView.addOnItemTouchListener(RecyclerClickListener(this,this,recyclerView))

    }
    private fun createUrl(tags: String): String{
        return Uri.Builder().scheme("https").path("//api.flickr.com/services/feeds/photos_public.gne")
            .appendQueryParameter("tags",tags)
            .appendQueryParameter("format","json")
            .appendQueryParameter("nojsoncallback","1")
            .build().toString()
//        return "https://api.flickr.com/services/feeds/photos_public.gne${tags.replace(" ","%20")}"
//        return "https://api.flickr.com/services/feeds/photos_public.gne?tags=adsense&format=json&nojsoncallback=1"

    }
    override fun sendFlickerJsonData(data: ArrayList<FlickerDataModel>) {
        runOnUiThread { recyclerAdepter.updateData(data) }
        Log.d(TAG, "sendFlickerJsonData: ${data[0].title}")
    }

    override fun recyclerSingleClick(position: Int) {
        if (BuildConfig.DEBUG){
            Log.d(TAG, "RecyclerSingleClick: ${recyclerAdepter.getItem(position).title}")
        }
        val i = Intent(this,PhotoDetailActivity::class.java)
        i.putExtra(PHOTOTRANSFER,recyclerAdepter.getItem(position))
        startActivity(i)

    }

    override fun recyclerLongClick(position: Int) {
        if (BuildConfig.DEBUG){
            Log.d(TAG, "RecyclerLongClick: ${recyclerAdepter.getItem(position).title}")
        }
        Toast.makeText(this,"Long click $position",Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainactivity_menu,menu)
        Log.d(TAG, "onCreateOptionsMenu: end")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: start")
        Log.d(TAG, "onOptionsItemSelected: itemid: ${item.itemId} mainbarid: ${R.id.main_manu_search}")
        return when (item.itemId) {
            R.id.main_manu_search -> {
                Log.d(TAG, "onOptionsItemSelected: mainActivity")
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val executer = Executors.newSingleThreadExecutor()
        val queary = getSharedPreferences("Main", MODE_PRIVATE).getString(SEARCH_QUERY,null)
        if (!lastQueary.equals(queary)){
            lastQueary = queary.toString()
            executer.execute {
            queary?.let { createUrl(it) }?.let { FlickerJsonData(this).getData(it) }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}