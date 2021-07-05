package com.popcorn.cakey.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R

class BlogListActivity :RecyclerView.Adapter<BlogListActivity.ViewHolder>(){
    private var intro= arrayOf("day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu",
        "day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu")
    private var title= arrayOf("Tieude1", "Tieude2","Tieude3", "Tieude4","Tieude5", "Tieude6","Tieude7", "Tieude8")
    private var image= intArrayOf(
        R.drawable.avatar, R.drawable.avatar, R.drawable.avatar,
        R.drawable.avatar, R.drawable.avatar, R.drawable.avatar, R.drawable.avatar, R.drawable.avatar)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogListActivity.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_main_blogs_list,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BlogListActivity.ViewHolder, position: Int) {
        holder.itemTitle.text=title[position]
        holder.itemIntro.text=intro[position]
        holder.itemImage.setImageResource(image[position])

    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.blogImage)
        var itemTitle: TextView = itemView.findViewById(R.id.blogTitle)
        var itemIntro:TextView=itemView.findViewById(R.id.blogIntro)


    }
}