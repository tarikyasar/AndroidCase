package com.example.protelandroidcase

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.example.protelandroidcase.news.News
import android.widget.ArrayAdapter as ArrayAdapter


class MainActivity : AppCompatActivity() {
    private var newsTitles = mutableListOf(
        "Test New 1",
        "Test New 2"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)


        fetchJson()

        val url = "https://newsapi.org/v2/everything?q=football&from=2021-08-18&sortBy=publishedAt&apiKey=" + BuildConfig.API_KEY

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val news = gson.fromJson(body, News::class.java)

                // val newsTitles = arrayOfNulls<String>(news.articles.size)

                for (i in 0 until news.articles.size) {
                    newsTitles.add(news.articles[i].title)
                    println("New: ${news.articles[i].title}")

                }

                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, newsTitles)

                println("Test Size: ${newsTitles.size}")
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute.")
            }
        })

        println("News Size: ${newsTitles.size}")
    }

    fun fetchJson() {
        println("Attempting to fetch json...")
    }
}