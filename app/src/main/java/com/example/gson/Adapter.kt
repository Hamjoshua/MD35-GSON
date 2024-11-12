package com.example.gson

// import android.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(private val list: ArrayList<Photo>,
                   private val onCLickImage: (String) -> Unit) :
        RecyclerView.Adapter<ImageAdapter.ViewHolder>(){
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
        Glide.with(holder.imageView).load(photo.getUri()).into((holder.imageView))
        holder.imageView.setOnClickListener{
            onCLickImage(photo.getUri().toString())
        }
    }
}