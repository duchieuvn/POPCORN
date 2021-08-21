package com.popcorn.cakey.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.popcorn.cakey.R

class BlogListActivity(private val bloglist: ArrayList<BlogThumbnails>) :
    RecyclerView.Adapter<BlogListActivity.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogListActivity.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main_blogs_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BlogListActivity.ViewHolder, position: Int) {
        val currentItem = bloglist[position]
        holder.itemTitle.text = currentItem.title
        holder.itemIntro.text = currentItem.intro
        holder.itemImage.setImageResource(currentItem.image)
    }

    override fun getItemCount(): Int {
        return bloglist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ShapeableImageView = itemView.findViewById(R.id.blogImage)
        var itemTitle: TextView = itemView.findViewById(R.id.blogTitle)
        var itemIntro: TextView = itemView.findViewById(R.id.blogIntro)
    }
}

