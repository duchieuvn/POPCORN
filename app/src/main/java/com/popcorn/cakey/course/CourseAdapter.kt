package com.popcorn.cakey.course

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.GetDataCallback
import com.parse.ParseException
import com.popcorn.cakey.R
import com.popcorn.cakey.mainscreen.BlogListActivity


class CourseAdapter(private val arrayList: ArrayList<ACourse>) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {
        val currentItem = arrayList[position]
        holder.title.text = currentItem.title
        val currentId=currentItem.ID
        currentItem.img.getDataInBackground(
            GetDataCallback(
                fun(data: ByteArray, e: ParseException) {
                    if (e == null) {
                        val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
                        holder.img.setImageBitmap(bmp)
                    } else {
                        holder.img.setImageResource(R.drawable.avatar)
                    }
                })
        )
        holder.img.setOnClickListener { v ->
            val i = Intent()
            if (v != null) {
                i.setClass(v.context, Course::class.java)
                i.putExtra("ObjectId", currentId)
                v.context.startActivities(arrayOf(i))
            }
        }
        holder.title.text = arrayList[position].title
        holder.author.text = arrayList[position].author
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.courseImage)
        var title: TextView = itemView.findViewById(R.id.courseTitle)
        var author: TextView = itemView.findViewById(R.id.courseAuthor)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

