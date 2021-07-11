package com.popcorn.cakey.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R

class SuggestionActivity:RecyclerView.Adapter<SuggestionActivity.ViewHolder>() {
    private var image= intArrayOf(
        R.drawable.avatar, R.drawable.avatar, R.drawable.avatar,
        R.drawable.avatar, R.drawable.avatar, R.drawable.avatar, R.drawable.avatar, R.drawable.avatar)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionActivity.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_suggestion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder:SuggestionActivity.ViewHolder, position: Int) {
        holder.itemImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return image.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.thumnail)

    }
}

