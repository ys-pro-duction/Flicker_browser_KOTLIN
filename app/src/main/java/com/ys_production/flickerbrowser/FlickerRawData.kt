package com.ys_production.flickerbrowser

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import kotlin.text.StringBuilder

class FlickerRawData(context: FlickerRawDataInter) {
    interface FlickerRawDataInter{
        fun sendFlickRawData(data: String)
    }
    private var callback: FlickerRawDataInter = context
    fun getData(url: String){
        try {
            Log.d("FlickerRawData", "getData: $url")
            val mUrl = URL(url)
            Log.d("FlickerRawData", "getData: $mUrl")
            val urlconnection = mUrl.openConnection()
            urlconnection.connect()
            Log.d("TAG", "getData: ${urlconnection.url}")
            val reader = BufferedReader(InputStreamReader(urlconnection.getInputStream()))
            val data: StringBuilder = StringBuilder()
            var line: String?
            while (null != reader.readLine().also { line = it }) {
                data.append(line)
            }
//        while (null != reader.readLine().also { line = it }) {
//            data.append(line).append("\n")
//        }
            callback.sendFlickRawData(data.toString())
        }catch (e: Exception){
            e.printStackTrace()
        }catch (e: MalformedURLException){
            e.printStackTrace()
        }catch(e: IOException){
            e.printStackTrace()
        }

    }
}