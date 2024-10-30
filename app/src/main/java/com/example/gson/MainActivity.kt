package com.example.gson

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

private var RView : RecyclerView? = null
private var contacts : ArrayList<Contact> = ArrayList<Contact>()
private var editableContacts : ArrayList<Contact> = ArrayList<Contact>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        HttpGetRequest().execute("https://drive.google.com/u/0/uc?id=1-KO" +
                "-9GA3NzSgIc1dkAsNm8Dqw0fuPxcR&export=download")

        RView = findViewById<RecyclerView>(R.id.r_view);
        RView?.layoutManager = LinearLayoutManager(this)

        val btn: Button = findViewById<Button>(R.id.btn_search);
        val editText: EditText = findViewById<EditText>(R.id.search_edit);

        btn.setOnClickListener{
            val line: String = editText.text.toString()
            if (line != ""){
                editableContacts = contacts.filter { it.name.contains(line) ||
                        it.type.contains(line) ||
                        it.phone.contains(line) } as ArrayList<Contact>;
            } else{
                editableContacts = contacts.clone() as ArrayList<Contact>;
            }
            RView?.adapter = CardAdapter(editableContacts);
        }
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
        val itemType = object : TypeToken<ArrayList<Contact>>() {}.type
        contacts = gson.fromJson<ArrayList<Contact>>(result, itemType);
        editableContacts = contacts.clone() as ArrayList<Contact>

        if(RView?.context != null){
            RView?.adapter = CardAdapter(editableContacts);
        }
    }

}

data class Contact(
    val phone: String,
    val name: String,
    val type: String
)