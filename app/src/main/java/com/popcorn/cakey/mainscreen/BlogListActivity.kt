package com.popcorn.cakey.mainscreen

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parse.GetDataCallback
import com.parse.ParseException
import com.popcorn.cakey.R
import com.popcorn.cakey.blog.ReadBlogActivity


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
        val currentId=currentItem.ID
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
                i.setClass(v.context, ReadBlogActivity::class.java)
                i.putExtra("ObjectId",currentId)
                v.context.startActivities(arrayOf(i))
            }
        }
        holder.itemAuthor.text=currentItem.author

    }

    override fun getItemCount(): Int {
        return bloglist.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.blogImage)
        var itemTitle: TextView = itemView.findViewById(R.id.blogTitle)
        var itemAuthor: TextView=itemView.findViewById(R.id.Author)


    }
}


