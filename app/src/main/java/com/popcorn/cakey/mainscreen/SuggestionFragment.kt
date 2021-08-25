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


class SuggestionFragment: Fragment(R.layout.activity_fragment1) {
    private lateinit var blogs: ArrayList<BlogThumbnails>
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SuggestionActivity.ViewHolder>? = null
    private lateinit var image: ArrayList<ParseFile>
    private lateinit var id: ArrayList<String>
    private val mew=R.drawable.avatar
    companion object{
        fun newInstance():SuggestionFragment{
            return SuggestionFragment()
        }
    }
    @SuppressLint("WrongThread")
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val queryBlog= ParseQuery.getQuery<ParseObject>("Blog").setLimit(8)
        val data = queryBlog?.orderByDescending("like")?.find()
        image=ArrayList()
        id=ArrayList()

        // null picture
        val icon= BitmapFactory.decodeResource(resources,mew)
        val stream= ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,100,stream)
        val byte= stream.toByteArray()
        val temp =ParseFile(byte)
        temp.saveInBackground()

        for( i in data?.indices!!){
            id.add(data[i].objectId)
            val pic=data[i].getParseFile("img")
            if(pic!=null){
                image.add(pic)
            }
            else{
                image.add(temp)
            }
        }
        val view: View=inflater.inflate(R.layout.activity_fragment1,container,false)
        layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView=view.findViewById(R.id.suggestionView)
        recyclerView.layoutManager=layoutManager
        getData()
        recyclerView.adapter=SuggestionActivity(blogs)
        return view
    }
    private fun getData(){
        blogs= arrayListOf()
        for (i in id.indices){
            val blog=BlogThumbnails(id[i],"null",image[i],"null")
            blogs.add(blog)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.suggestionView.apply{
            layoutManager=LinearLayoutManager(activity)
            adapter=SuggestionActivity(blogs)
        }
    }
}
