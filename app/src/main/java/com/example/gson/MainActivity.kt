package com.example.gson

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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

        Timber.plant(Timber.DebugTree())

        HttpGetRequest().execute("https://drive.google.com/u/0/uc?id=1-KO" +
                "-9GA3NzSgIc1dkAsNm8Dqw0fuPxcR&export=download")

        RView = findViewById<RecyclerView>(R.id.r_view);

        RView?.layoutManager = LinearLayoutManager(this);
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
        val wrapper: ArrayList<Contact> = gson.fromJson(result, ArrayList<Contact>()::class.java);
        var counter: Int = 1

        if(RView?.context != null){
            RView?.adapter = CardAdapter(wrapper);
        }
    }

}

data class Contact(
    val phone: String,
    val name: String,
    val type: String
)