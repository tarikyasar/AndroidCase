package com.example.protelandroidcase

import android.app.DatePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.example.protelandroidcase.NewsObjects.News
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var cal = java.util.Calendar.getInstance()

    // Tekrar tekrar kullanilacagi icin tarih formati icin degisken olustur
    private val dateformat = "yyyy-MM-dd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Action bar'i kapat (estetik kaygilar sebebiyle)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_main)

        val textviewDate = findViewById<TextView>(R.id.textViewDate1)
        val buttonDate = findViewById<Button>(R.id.buttonDate1)

        buttonDate.setBackgroundColor(Color.WHITE)
        buttonDate.setTextColor(Color.BLACK)

        // Bugunden 10 gun once basla
        textviewDate.text = getCalculatedDate(dateFormat = dateformat, days = -10)

        // DatePickerDialog olustur
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        // Tarih secme butonu icin listener olustur ve default tarihi
        // bugunden 10 gun oncesine ayarla
        buttonDate.setOnClickListener {
            DatePickerDialog(this@MainActivity,
                dateSetListener,
                // DatePickerDialog bugunun 10 gun oncesinden baslar
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH) - 10).show()
        }

        // Haberleri goruntule
        renderNews(fromDate = textviewDate.text.toString(), toDate = getToday(dateFormat = dateformat))
    }

    // Haberleri goruntuleme fonksiyonu
    private fun renderNews(fromDate: String, toDate: String) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // RecyclerView icerisindeki elemenlara divider olusturur
        recyclerView.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL
            )
        )

        val url = "https://newsapi.org/v2/everything?q=football&from=" + fromDate + "&to=" + toDate + "&sortBy=publishedAt&pageSize=100&apiKey=" + BuildConfig.API_KEY

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val news = gson.fromJson(body, News::class.java)

                // Request background thread'inde calistigi icin View'da degisiklik yapamiyor
                // Bunu asmak icin MainAdapter UI Thread'inde cagrilir
                runOnUiThread {
                    recyclerView.adapter = MainAdapter(news.articles)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute.")
            }
        })
    }

    private fun updateDateInView() {
        val sdf = SimpleDateFormat(dateformat, Locale.US)
        val textviewDate = findViewById<TextView>(R.id.textViewDate1)

        textviewDate.text = sdf.format(cal.getTime())

        renderNews(fromDate = textviewDate.text.toString(), toDate = getToday(dateFormat = dateformat))
    }

    private fun getToday(dateFormat: String?): String {
        val cal = java.util.Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)

        return s.format(Date(cal.timeInMillis))
    }

    private fun getCalculatedDate(dateFormat: String?, days: Int): String? {
        val cal = java.util.Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)

        cal.add(Calendar.DAY_OF_YEAR, days)

        return s.format(Date(cal.timeInMillis))
    }
}