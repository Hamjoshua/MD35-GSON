package com.example.gson

import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL


private var RView : RecyclerView? = null
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gridLayoutManager = GridLayoutManager(this, 2)

        Timber.plant(Timber.DebugTree())

        RView = findViewById<RecyclerView>(R.id.rView);

        RView?.setHasFixedSize(true)
        RView?.layoutManager = gridLayoutManager

        HttpGetRequest().execute("https://api.flickr.com/services/rest/" +
                "?method=flickr.photos.search&api_key" +
                "=ff49fcd4d4a08aa6aafb6ea3de826464&tags=cat&format=json&nojsoncallback=1")


    }
}

class HttpGetRequest : AsyncTask<String, Void, String>(){
    override fun doInBackground(vararg params: String?): String {
        var text: String = "";
        val url: URL = URL(params[0])
        val urlConn: HttpURLConnection = url.openConnection() as HttpURLConnection
        text = urlConn.inputStream.bufferedReader().readText()

        return text;
    }

    override fun onPostExecute(result: String?) {
        val gson = Gson();
        val wrapper: Wrapper = gson.fromJson(result, Wrapper::class.java);
        var counter: Int = 1

        if(RView?.context != null){
            RView?.adapter = ImageAdapter(RView?.context!!, wrapper.photos.photo);
        }


        for (photo in wrapper.photos.photo){
            if(counter % 5 == 0){
                Timber.i(photo.toString());
            }

            ++counter;
        }
    }
}

data class Photo(val id: String,
                 val owner: String,
                 val secret: String,
                 val server: String,
                 val farm: Int,
                 val title: String,
                 val ispublic: Int,
                 val isfriend: Int,
                 val isfamily: Int){
    fun getUri() : Uri{
        val address = "https://farm${this.farm}.staticflickr.com" +
                "/${this.server}/${this.id}_${this.secret}_z.jpg"

        val uri: Uri = Uri.parse(address)

        return uri;
    }
}

data class PhotoPage(val photo: ArrayList<Photo>,
                     val page: Int,
                     val pages: Int,
                     val total: Int,
                     val perpage: Int){

}

data class Wrapper(val photos: PhotoPage) {

}