package com.popcorn.cakey.mainscreen


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.popcorn.cakey.R
import java.io.ByteArrayOutputStream


class BlogListFragment: Fragment(R.layout.activity_fragment2),SearchView.OnQueryTextListener {
    private lateinit var bloglist: ArrayList<BlogThumbnails>
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: BlogListActivity? = null
    private lateinit var title: ArrayList<String>
    private lateinit var image: ArrayList<ParseFile>
    private lateinit var author: ArrayList<String>
    private val mew=R.drawable.avatar
    private lateinit var blogid: ArrayList<String>
    companion object{
        fun newInstance(): BlogListFragment {
           return BlogListFragment()
        }
    }

    @SuppressLint("WrongThread")
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)

        val view: View=inflater.inflate(R.layout.activity_fragment2,container,false)
        val queryBlog= ParseQuery.getQuery<ParseObject>("Blog").setLimit(10)
        val data = queryBlog?.orderByDescending("updateAt")?.find()
        title=ArrayList()
        image=ArrayList()
        blogid=ArrayList()
        author= ArrayList()

        // null picture
        val icon= BitmapFactory.decodeResource(resources,mew)
        val stream= ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG,100,stream)
        val byte= stream.toByteArray()
        val temp = ParseFile(byte)

        for (i in data?.indices!!){
            val pics=data[i].getParseFile("img")
            var name = data[i].getParseUser("author")?.fetchIfNeeded()?.get("username")
            if(name== null){
                name = "Example Author"
            }
            title.add(data[i].getString("name").toString())
            author.add(name as String)
            blogid.add(data[i].objectId)
            if(pics!=null){
                image.add(pics)
            }
            else{
                image.add(temp)
            }

        }
        getdata()
        adapter= BlogListActivity()
        adapter!!.setData(bloglist)

        layoutManager= LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        recyclerView=view.findViewById(R.id.MainBlogList)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter

        return view

    }

    private fun getdata(){
        bloglist= arrayListOf()
        for (i in title.indices){
            val blog=BlogThumbnails(blogid[i],title[i],image[i],author[i])
            bloglist.add(blog)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.MainBlogList.apply {
            layoutManager=LinearLayoutManager(activity)
            adapter
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    @Override
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)
        val menuItem= menu.findItem(R.id.app_bar_search)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView = menuItem!!.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint="enter blog's name"
        super.onCreateOptionsMenu(menu, inflater)



    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter!!.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter!!.filter.filter(newText)
        return true
    }


}



