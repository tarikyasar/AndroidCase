package com.example.protelandroidcase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.example.protelandroidcase.news.News
import java.lang.NullPointerException
import java.util.*
import android.widget.ArrayAdapter as ArrayAdapter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_main)

        renderNews()
    }

    fun renderNews() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL
            )
        )

        val url = "https://newsapi.org/v2/everything?q=football&from=2021-08-18&sortBy=publishedAt&apiKey=" + BuildConfig.API_KEY

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val news = gson.fromJson(body, News::class.java)

                runOnUiThread {
                    recyclerView.adapter = MainAdapter(news.articles)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute.")
            }
        })
        println("Attempting to fetch json...")
    }
}