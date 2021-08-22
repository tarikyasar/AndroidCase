package com.example.protelandroidcase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.protelandroidcase.NewsObjects.Article
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


class MainAdapter(private val dataSet: List<Article>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newTitle: TextView
        val newDescription: TextView
        val newURL: TextView
        val newImage: ImageView

        init {
            newTitle = view.findViewById(R.id.textView_new_title)
            newDescription = view.findViewById(R.id.textView_new_description)
            newURL = view.findViewById(R.id.newURL)
            newImage = view.findViewById(R.id.newImage)

            // Haber view'una tiklandiginda url'
            view.setOnClickListener {
                val intent = Intent(view.context, NewWebView::class.java)
                intent.putExtra("newURL", newURL.text)

                view.context.startActivity(intent)
            }
        }
    }

    // Yeni Viewlar olustur
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Bir liste elemani olarak view olustur
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.new_row, viewGroup, false)

        return ViewHolder(view)
    }

    // View'un icerigi degistirilir
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Datasetten veri alinir ve view'in icerigi o veri ile degistirilir
        viewHolder.newTitle.text = dataSet[position].title
        viewHolder.newDescription.text = dataSet[position].description
        viewHolder.newURL.text = dataSet[position].url
        val newThumbnailImage = dataSet[position].urlToImage
        println("New Title: ${dataSet[position].title} Date: ${dataSet[position].publishedAt}")

        Picasso.get().load(newThumbnailImage)
            .into(viewHolder.newImage)
    }

    // news.articles datasetinin boyutunu dondur
    override fun getItemCount() = dataSet.size
}