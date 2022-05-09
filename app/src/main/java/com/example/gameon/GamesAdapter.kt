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
    private var backgroundImg = ""

    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
        val name = itemView.findViewById<TextView>(R.id.textViewName)
        val releaseDate = itemView.findViewById<TextView>(R.id.textViewReleaseDate)
        val metacritic = itemView.findViewById<TextView>(R.id.textViewMetaCritic)
        val profileImage = itemView.findViewById<ImageView>(R.id.imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = results[position]
        holder.name.text = currentItem.name
        holder.releaseDate.text = "Released on: ${currentItem.released}"
        holder.metacritic.text = currentItem.metacritic.toString()
        when (holder.metacritic.text.toString().toInt()) {
            in 90..100 -> {
                holder.metacritic.setTextColor((Color.parseColor("#22830B")))
            }
            in 80..89 -> {
                holder.metacritic.setTextColor((Color.parseColor("#FF8BC34A")))
            }
            in 70..79 -> {
                holder.metacritic.setTextColor((Color.parseColor("#FFFFC107")))
            }
            else -> {
                holder.metacritic.setTextColor((Color.parseColor("#FFCA0A10")))
            }
        }

        val context = holder.itemView.context

        Glide.with(context)
            .load(currentItem.background_image)
            .placeholder(R.drawable.ic_baseline_games_24)
            .into(holder.profileImage)
    }


    override fun getItemCount(): Int {
        return results.size
    }

}