package com.ys_production.flickerbrowser

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException

class FlickerJsonData(context: FlickerJsonDataInter) : FlickerRawData.FlickerRawDataInter {
    private val callback : FlickerJsonDataInter = context
    interface FlickerJsonDataInter {
        fun sendFlickerJsonData(data: ArrayList<FlickerDataModel>)
    }

    fun getData(mUrl: String) {
        try {
            FlickerRawData(this).getData(mUrl)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun sendFlickRawData(data: String) {
        try {
            val itemsObject = JSONObject(data).getJSONArray("items")
            val datalist = ArrayList<FlickerDataModel>(itemsObject.length())
            for (i in 0 until itemsObject.length()){
                val title = itemsObject.getJSONObject(i).getString("title")
                val link = itemsObject.getJSONObject(i).getString("link")
                val imglink = itemsObject.getJSONObject(i).getJSONObject("media")
                    .getString("m")
//                    .replace("_m","_b")
                val published = itemsObject.getJSONObject(i).getString("published")
                val author = itemsObject.getJSONObject(i).getString("author")
                    .replace("nobody@flickr.com (\"","").replace("\")","")
                val tags = itemsObject.getJSONObject(i).getString("tags")
                datalist.add(FlickerDataModel(title,link, imglink, published, author, tags))
            }
            callback.sendFlickerJsonData(datalist)
        }catch (e: JSONException){
            Log.d(TAG, "sendFlickRawData: ${e.message}")
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "FlickerJsonData"
    }
}