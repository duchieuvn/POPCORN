package com.popcorn.cakey.mainscreen

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.parse.GetDataCallback
import com.parse.ParseException
import com.popcorn.cakey.R
import com.popcorn.cakey.blog.ReadBlogActivity

class SuggestionActivity(private val Blogs: ArrayList<BlogThumbnails>):RecyclerView.Adapter<SuggestionActivity.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionActivity.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_suggestion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder:SuggestionActivity.ViewHolder, position: Int) {
        val currentItem= Blogs[position]
        currentItem.image.getDataInBackground(
            GetDataCallback(
                fun(data: ByteArray, _: ParseException) {
                    val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
                    holder.itemImage.setImageBitmap(bmp)
                })
        )
        holder.itemImage.setOnClickListener { v ->
            val i = Intent()
            if (v != null) {
                i.setClass(v.context,ReadBlogActivity::class.java)
                i.putExtra("ObjectId",currentItem.ID)
                v.context.startActivities(arrayOf(i))
            }
        }
    }

    override fun getItemCount(): Int {
        return Blogs.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.thumnail)

    }
}

