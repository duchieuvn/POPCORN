package com.popcorn.cakey.mainscreen

import android.annotation.SuppressLint
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

class SuggestionActivity()
    :RecyclerView.Adapter<SuggestionActivity.ViewHolder>() {
    private var Blogs: ArrayList<Blog> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionActivity.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_suggestion,parent,false)
        return ViewHolder(v)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(bloglist: ArrayList<Blog>) {
        this.Blogs = bloglist
        notifyDataSetChanged()


    }
    override fun onBindViewHolder(holder:SuggestionActivity.ViewHolder, position: Int) {
        val currentItem= Blogs[position]
        currentItem.image.getDataInBackground(
            GetDataCallback(
                fun(data: ByteArray, e: ParseException) {
                    if(e==null){
                        val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
                        holder.itemImage.setImageBitmap(bmp)
                    }
                    else{
                        holder.itemImage.setImageResource(R.drawable.avatar)
                    }
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

