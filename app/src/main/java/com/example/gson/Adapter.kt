package com.example.gson

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val list: ArrayList<Photo>) :
        RecyclerView.Adapter<Adapter.ViewHolder>(){
            class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val imageView = itemView.findViewById<ImageView>(R.id.rImage)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.r_item,
            parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = list[position];
        holder.imageView.setImageURI()
    }
}