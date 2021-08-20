package com.example.protelandroidcase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.protelandroidcase.news.Article
import com.squareup.picasso.Picasso

class MainAdapter(val dataSet: List<Article>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newTitle: TextView
        val newDescription: TextView
        val newImage: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            newTitle = view.findViewById(R.id.textView_new_title)
            newDescription = view.findViewById(R.id.textView_new_description)
            newImage = view.findViewById(R.id.newImage)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.new_row, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.newTitle.text = dataSet[position].title
        viewHolder.newDescription.text = dataSet[position].description
        val newThumbnailImage = dataSet[position].urlToImage
        println("Image URL: ${newThumbnailImage}")

        Picasso.get().load(newThumbnailImage)
            .into(viewHolder.newImage)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}