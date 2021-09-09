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
import com.parse.FindCallback
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.popcorn.cakey.R
import java.io.ByteArrayOutputStream


class SuggestionFragment: Fragment(R.layout.activity_fragment1) {
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: SuggestionActivity? = null
    private var blogs: ArrayList<Blog> = arrayListOf()
    //private lateinit var image: ArrayList<ParseFile>
    //private lateinit var id: ArrayList<String>
    private val mew=R.drawable.avatar
    companion object{
        fun newInstance():SuggestionFragment{
            return SuggestionFragment()
        }
    }
    @SuppressLint("WrongThread")
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //val queryBlog= ParseQuery.getQuery<ParseObject>("Blog").setLimit(8)
        //val data = queryBlog?.orderByDescending("like")?.find()
        //image=ArrayList()
        //id=ArrayList()

        // null picture
        val icon= BitmapFactory.decodeResource(resources,mew)
        val stream= ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,100,stream)
        val byte= stream.toByteArray()
        val placeholder =ParseFile(byte)

        //for( i in data?.indices!!){
        //    id.add(data[i].objectId)
        //    val pic=data[i].getParseFile("img")
        //    if(pic!=null){
        //        image.add(pic)
        //    }
        //    else{
        //        image.add(temp)
        //    }
        //}
        adapter = SuggestionActivity()
        adapter!!.setData(blogs)

        querySuggestionInBackground(10, 1, "like") { res, e ->
            if (e == null) {
                for (blog in res) {
                    if (blog != null) {
                        val id = blog.objectId
                        val thumbnail = blog.getParseFile("img") ?: placeholder
                        blogs.add(Blog(id,"null",thumbnail,"null"))
                        // Update the adapter as we appending blog
                        adapter!!.notifyItemChanged(blogs.size - 1)
                    }
                }
            } else {
                // NO OBJECT FOUND
            }
        }
        val view: View=inflater.inflate(R.layout.activity_fragment1,container,false)
        layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView=view.findViewById(R.id.suggestionView)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter= adapter
        return view
    }
    private fun querySuggestionInBackground(
        limit: Int = 8,
        orderBy: Int = 0,
        orderByKey: String? = null,
        includeKey: String? = null,
        callback: FindCallback<ParseObject?>? = null
    ) {
        val queryBlog = ParseQuery.getQuery<ParseObject>("Blog")
        queryBlog.limit = limit
        when (orderBy) {
            1 -> queryBlog.orderByAscending(orderByKey)
            -1 -> queryBlog.orderByDescending(orderByKey)
        }
        if (includeKey != null) queryBlog.include(includeKey)
        if (callback != null) queryBlog.findInBackground(callback)
        else queryBlog.findInBackground()
    }
    //private fun getData(){
    //    blogs= arrayListOf()
    //    for (i in id.indices){
    //        val blog=Blog(id[i],"null",image[i],"null")
    //        blogs.add(blog)
    //    }
    //}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.suggestionView.apply{
            layoutManager=LinearLayoutManager(activity)
        }
    }
}
