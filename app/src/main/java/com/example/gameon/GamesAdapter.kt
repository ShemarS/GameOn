package com.example.gameon

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_item.view.*


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



        init {
            itemView.setOnClickListener {
                val selectedItem = adapterPosition
                val title = itemView.textViewName.text.toString()

                val myIntent = Intent(itemView.context, GameProfile::class.java)
                myIntent.putExtra("title", title)

                //Log.d(TAG, "title: $title")
                //Log.d(TAG, "value to be passed: $what")

                itemView.context.startActivity(myIntent)


                //itemView.context.startActivity(Intent(itemView.context, GameProfile::class.java))

               // viewModel.setInfo(title, metacritic)
/*                Toast.makeText(itemView.context, "You clicked on $selectedItem heres the name? : ${itemView.textViewName.text}",
                    Toast.LENGTH_SHORT).show()*/

            }
        }
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
        val metaValue = holder.metacritic.text.toString().toInt()
        if(metaValue in 90..100) {
            holder.metacritic.setTextColor((Color.parseColor("#22830B")))
        }
        else if(metaValue in 80..89) {
            holder.metacritic.setTextColor((Color.parseColor("#FF8BC34A")))
        }
        else if(metaValue in 70..79) {
            holder.metacritic.setTextColor((Color.parseColor("#FFFFC107")))
        }
        else {
            holder.metacritic.setTextColor((Color.parseColor("#FFCA0A10")))
        }

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