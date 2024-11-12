package com.example.gson

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    // public lateinit var RView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gridLayoutManager = GridLayoutManager(this, 2)

        Timber.plant(Timber.DebugTree())

        var RView: RecyclerView = findViewById<RecyclerView>(R.id.rView);

        RView.setHasFixedSize(true)
        RView.layoutManager = gridLayoutManager

        CoroutineScope(Dispatchers.IO).launch {
            val url: URL = URL("https://api.flickr.com/services/rest/" +
                    "?method=flickr.photos.search&api_key" +
                    "=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")
            val urlConn: HttpURLConnection = url.openConnection() as HttpURLConnection
            var text: String = urlConn.inputStream.bufferedReader().readText()

            val gson = Gson();
            val wrapper: Wrapper = gson.fromJson(text, Wrapper::class.java);
            var counter: Int = 1

            withContext(Dispatchers.Main) {
                if (RView.context != null) {
                    RView.adapter = ImageAdapter(RView.context!!, wrapper.photos.photo);
                }


                for (photo in wrapper.photos.photo) {
                    if (counter % 5 == 0) {
                        Timber.i(photo.toString());
                    }

                    ++counter;
                }
            }
        }

        runBlocking{
            launch{



            }
        }
    }
}


data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
) {
    fun getUri(): Uri {
        val address = "https://farm${this.farm}.staticflickr.com" +
                "/${this.server}/${this.id}_${this.secret}_z.jpg"

        val uri: Uri = Uri.parse(address)

        return uri;
    }
}

data class PhotoPage(
    val photo: ArrayList<Photo>,
    val page: Int,
    val pages: Int,
    val total: Int,
    val perpage: Int
) {

}

data class Wrapper(val photos: PhotoPage) {

}