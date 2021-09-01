package com.popcorn.cakey.mainscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseException
import com.popcorn.cakey.R
import com.popcorn.cakey.blog.ReadBlogActivity
import java.util.*
import kotlin.collections.ArrayList


class BlogListActivity :
    RecyclerView.Adapter<BlogListActivity.ViewHolder>(), Filterable {
    private var itemListFilter: ArrayList<Blog> = ArrayList()
    private var bloglist: ArrayList<Blog> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogListActivity.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main_blogs_list, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(bloglist: ArrayList<Blog>) {
        this.bloglist = bloglist
        this.itemListFilter = bloglist
        notifyDataSetChanged()


    }

    override fun onBindViewHolder(holder: BlogListActivity.ViewHolder, position: Int) {
        val currentItem = bloglist[position]
        holder.itemTitle.text = currentItem.title
        val currentId = currentItem.ID
        currentItem.image.getDataInBackground { data, e ->
            if (e == null) {
                val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
                holder.itemImage.setImageBitmap(bmp)
            } else {
                holder.itemImage.setImageResource(R.drawable.avatar)
            }
        }

        holder.itemImage.setOnClickListener { v ->
            val i = Intent()
            if (v != null) {
                i.setClass(v.context, ReadBlogActivity::class.java)
                i.putExtra("ObjectId", currentId)
                v.context.startActivities(arrayOf(i))
            }
        }
        holder.itemAuthor.text = currentItem.author

    }

    override fun getItemCount(): Int {
        return bloglist.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.blogImage)
        var itemTitle: TextView = itemView.findViewById(R.id.blogTitle)
        var itemAuthor: TextView = itemView.findViewById(R.id.Author)


    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (charSequence != null) {
                    if (charSequence == "" || charSequence.length < 0) {
                        filterResults.count = itemListFilter.size
                        filterResults.values = itemListFilter
                    } else {
                        val searchChr = charSequence.toString().lowercase(Locale.getDefault())

                        val blogs = ArrayList<Blog>()

                        for (item in bloglist) {
                            if (item.title?.lowercase()
                                    ?.contains(searchChr)!! || item.author?.lowercase()
                                    ?.contains(searchChr)!!
                            ) {
                                blogs.add(item)
                            }
                        }
                        filterResults.count = blogs.size
                        filterResults.values = blogs
                    }
                }
                return filterResults
            }


            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                bloglist = results?.values as ArrayList<Blog>
                notifyDataSetChanged()
            }

        }
    }
}


