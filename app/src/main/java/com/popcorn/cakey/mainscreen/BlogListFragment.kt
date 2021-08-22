package com.popcorn.cakey.mainscreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.popcorn.cakey.R
import java.io.ByteArrayOutputStream


class BlogListFragment: Fragment(R.layout.activity_fragment2) {
    private lateinit var bloglist: ArrayList<BlogThumbnails>
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BlogListActivity.ViewHolder>? = null
    private lateinit var title: ArrayList<String>
    private lateinit var image: ArrayList<ParseFile>
    private val meo=R.drawable.avatar
    private lateinit var blogid: ArrayList<String>
    companion object{
        fun newInstance(): BlogListFragment {
           return BlogListFragment()
        }
    }

    @SuppressLint("WrongThread")
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View=inflater.inflate(R.layout.activity_fragment2,container,false)
        val queryBlog= ParseQuery.getQuery<ParseObject>("Blog").setLimit(20)
        val data = queryBlog?.find()
        title=ArrayList()
        image=ArrayList()
        blogid=ArrayList()
        val icon=BitmapFactory.decodeResource(resources,meo)
        val stream= ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,100,stream)
        val byte= stream.toByteArray()
        val temp =ParseFile(byte)

        for (i in data?.indices!!){
            val pics=data[i].getParseFile("img")

            if(pics!=null){
                image.add(pics)
                title.add(data[i].getString("name").toString())
                blogid.add(data[i].objectId)
            }
            else{
                title.add(data[i].getString("name").toString())
                blogid.add(data[i].objectId)
                image.add(temp)
            }

        }


        /*image= arrayOf(
           R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
            R.drawable.avatar,
        )*/

        layoutManager= LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        recyclerView=view.findViewById(R.id.MainBlogList)
        recyclerView.layoutManager=layoutManager

        getData()
        recyclerView.adapter=BlogListActivity(bloglist)
        return view

    }
    private fun getData(){
        bloglist= arrayListOf()
        for (i in title.indices){
            val blog=BlogThumbnails(blogid[i],title[i],image[i],"Truong")
            bloglist.add(blog)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        R.id.MainBlogList.apply {
            layoutManager=LinearLayoutManager(activity)
            adapter=BlogListActivity(bloglist)

        }
    }

}