package com.example.protelandroidcase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.protelandroidcase.NewsObjects.Article
import com.squareup.picasso.Picasso

class MainAdapter(val dataSet: List<Article>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newTitle: TextView
        val newDescription: TextView
        val newImage: ImageView

        init {
            newTitle = view.findViewById(R.id.textView_new_title)
            newDescription = view.findViewById(R.id.textView_new_description)
            newImage = view.findViewById(R.id.newImage)
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
        val newThumbnailImage = dataSet[position].urlToImage
        println("New Title: ${dataSet[position].title} Date: ${dataSet[position].publishedAt}")

        Picasso.get().load(newThumbnailImage)
            .into(viewHolder.newImage)
    }

    // news.articles datasetinin boyutunu dondur
    override fun getItemCount() = dataSet.size
}