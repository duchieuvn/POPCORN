package com.popcorn.cakey.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseObject
import com.parse.ParseQuery
import com.popcorn.cakey.R


class BlogListFragment: Fragment(R.layout.activity_fragment2) {
    private lateinit var bloglist: ArrayList<BlogThumbnails>
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BlogListActivity.ViewHolder>? = null
    private val queryBlog: ParseQuery<ParseObject>? = ParseQuery.getQuery<ParseObject>("Blog").setLimit(8)
    private lateinit var title: Array<String>
    private lateinit var intro: Array<String>
    private lateinit var image: Array<Int>

    companion object{
        fun newInstance(): BlogListFragment {
           return BlogListFragment()
        }
    }
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View=inflater.inflate(R.layout.activity_fragment2,container,false)
        val data =  queryBlog?.find()
        for (item in data?.indices!!){
            title.plusElement(data[item].getString("name").toString())
        }




        //title= arrayOf("Tieude1", "Tieude2","Tieude3", "Tieude4","Tieude5", "Tieude6","Tieude7", "Tieude8")
        intro= arrayOf("day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu",
            "day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu","day la loi gioi thieu")

        image= arrayOf(
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
        )

        layoutManager= LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        recyclerView=view.findViewById(R.id.MainBlogList)
        recyclerView.layoutManager=layoutManager
        bloglist= arrayListOf()
        for (i in intro.indices){
            val blog=BlogThumbnails(intro[i],title[i],image[i])
            bloglist.add(blog)
        }

        recyclerView.adapter=BlogListActivity(bloglist)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.MainBlogList.apply {
            layoutManager=LinearLayoutManager(activity)
            adapter=BlogListActivity(bloglist)

        }
    }
}