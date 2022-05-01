package com.example.gameon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class GamesAdapter(private val results: ArrayList<Results>) : RecyclerView.Adapter<GamesAdapter.MyViewHolder>() {

    private val TAG = "MAN WHAT"

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
        // This class will represent a single row in our recyclerView list
        // This class also allows caching views and reuse them
        // Each MyViewHolder object keeps a reference to 3 view items in our row_item.xml file
        val name = itemView.findViewById<TextView>(R.id.textViewName)
        val releaseDate = itemView.findViewById<TextView>(R.id.textViewReleaseDate)
        val metacritic = itemView.findViewById<TextView>(R.id.textViewMetaCritic)
        val profileImage = itemView.findViewById<ImageView>(R.id.imageView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate a layout from our XML (row_item.XML) and return the holder
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val currentItem = results[position]
        holder.name.text = currentItem.name
        holder.releaseDate.text = "Released on: ${currentItem.released}"
        holder.metacritic.text = currentItem.metacritic.toString()

        // Get the context for glide
        val context = holder.itemView.context

        // Load the image from the url using Glide library
        Glide.with(context)
            .load(currentItem.background_image)
            .placeholder(R.drawable.ic_baseline_games_24) // In case the image is not loaded show this placeholder image
            .into(holder.profileImage)
    }


    override fun getItemCount(): Int {
        // Return the size of your dataset (invoked by the layout manager)
        return results.size
    }

}