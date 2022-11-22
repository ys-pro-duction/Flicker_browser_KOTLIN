package com.ys_production.flickerbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdepter(private var list: ArrayList<FlickerDataModel>):
    RecyclerView.Adapter<RecyclerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        return RecyclerHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        if (list.isNotEmpty()){
            val item = list[position]
            holder.thumbnailText.text = item.title
            if (position == 5){
                Picasso.get().load("${item.imglink}....").placeholder(R.drawable.ic_baseline_image)
                    .error(R.drawable.ic_baseline_error).into(holder.thumbnailImage)
            }else{
                Picasso.get().load(item.imglink).placeholder(R.drawable.ic_baseline_image)
                    .error(R.drawable.ic_launcher_foreground).into(holder.thumbnailImage)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 0 else list.size
    }
    fun updateData(newList: ArrayList<FlickerDataModel>){
        list = newList
        notifyDataSetChanged()
    }
    fun getItem(position: Int): FlickerDataModel{
        return list[position]
    }
}

class RecyclerHolder(view: View) : RecyclerView.ViewHolder(view){
    val thumbnailImage = view.findViewById<ImageView>(R.id.item_imageView)
    val thumbnailText = view.findViewById<TextView>(R.id.item_textView)
}
