package com.popcorn.cakey.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R

class SuggestionActivity:RecyclerView.Adapter<SuggestionActivity.ViewHolder>() {
    private var title= arrayOf("anh1", "anh2","anh3", "anh4","anh5", "anh6","anh7", "anh8")
    private var image= intArrayOf(
        R.drawable.avatar, R.drawable.avatar, R.drawable.avatar,
        R.drawable.avatar, R.drawable.avatar, R.drawable.avatar, R.drawable.avatar, R.drawable.avatar)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionActivity.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_suggestion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder:SuggestionActivity.ViewHolder, position: Int) {
        holder.itemTitle.text=title[position]
        holder.itemImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.thumnail)
        var itemTitle: TextView = itemView.findViewById(R.id.username)

    }
}

