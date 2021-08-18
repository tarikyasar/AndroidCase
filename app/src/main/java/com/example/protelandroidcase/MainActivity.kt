package com.example.protelandroidcase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.example.protelandroidcase.news.News



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchJson()
    }

    fun fetchJson() {
        println("Attempting to fetch json...")

        val url = "https://newsapi.org/v2/everything?q=football&from=2021-08-18&sortBy=publishedAt&apiKey=" + BuildConfig.API_KEY

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val news = gson.fromJson(body, News::class.java)

                println(body)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute.")
            }
        })
    }
}
