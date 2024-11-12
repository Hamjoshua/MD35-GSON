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

class ImageAdapter(private val context: Context,
              private val list: ArrayList<Photo>) :
        RecyclerView.Adapter<ImageAdapter.ViewHolder>(){
            class ViewHolder(view: View): RecyclerView.ViewHolder(view){
                val imageView = itemView.findViewById<ImageView>(R.id.rImage)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.r_item,
            parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = list[position];
        Glide.with(context).load(photo.getUri()).into((holder.imageView))
        holder.imageView.setOnClickListener{
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                    as ClipboardManager
            val clipData = ClipData.newPlainText("Copied Text", photo.getUri().toString())
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, photo.getUri().toString(), Toast.LENGTH_SHORT).show()
        }
    }
}