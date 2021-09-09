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
import com.parse.FindCallback
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.popcorn.cakey.R
import java.io.ByteArrayOutputStream


class BlogListFragment : Fragment(R.layout.activity_fragment2), SearchView.OnQueryTextListener {
    private lateinit var title: ArrayList<String>
    private lateinit var image: ArrayList<ParseFile>
    private lateinit var author: ArrayList<String>
    private lateinit var blogid: ArrayList<String>
    private lateinit var recyclerView: RecyclerView
    private var blogList: ArrayList<Blog> = arrayListOf()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: BlogListActivity? = null
    private val mew = R.drawable.avatar

    companion object {
        fun newInstance(): BlogListFragment {
            return BlogListFragment()
        }
    }

    @SuppressLint("WrongThread")
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val view: View = inflater.inflate(R.layout.activity_fragment2, container, false)
       // val queryBlog = ParseQuery.getQuery<ParseObject>("Blog").setLimit(10)
        //val data = queryBlog?.orderByDescending("updateAt")?.find()
        //title = ArrayList()
        //image = ArrayList()
        //blogid = ArrayList()
        //author = ArrayList()

        // null picture
        val icon = BitmapFactory.decodeResource(resources, mew)
        val stream = ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byte = stream.toByteArray()
        val placeholder = ParseFile(byte)

        // for (i in data?.indices!!) {
        //     val pics = data[i].getParseFile("img")
        //     var name = data[i].getParseUser("author")?.fetchIfNeeded()?.get("username")
        //     if (name == null) {
        //         name = "Example Author"
        //     }
        //     title.add(data[i].getString("name").toString())
        //     author.add(name as String)
        //     blogid.add(data[i].objectId)
        //     if (pics != null) {
        //         image.add(pics)
        //     } else {
        //         image.add(temp)
        //     }
        //
        // }
        // getdata()
        adapter = BlogListActivity()
        adapter!!.setData(blogList)

        queryBlogListInBackground(10, 1, "updateAt", "author.username") { res, e ->
            if (e == null) {
                for (blog in res) {
                    if (blog != null) {
                        val id = blog.objectId
                        val title = blog.getString("name")
                        val author = blog.getParseUser("author")
                        // Already included username, so no need to fetch
                        val username = author?.username ?: ""
                        val thumbnail = blog.getParseFile("img") ?: placeholder
                        blogList.add(Blog(id, title, thumbnail, username))
                        // Update the adapter as we appending blog
                        adapter!!.notifyItemChanged(blogList.size - 1)
                    }
                }
            } else {
                // NO OBJECT FOUND
            }
        }

        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.MainBlogList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        return view
    }

    private fun queryBlogListInBackground(
        limit: Int = 100,
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

    //private fun getdata() {
    //    blogList = arrayListOf()
    //    for (i in title.indices) {
    //        val blog = Blog(blogid[i], title[i], image[i], author[i])
    //        blogList.add(blog)
    //    }
    //}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.MainBlogList.apply {
            layoutManager = LinearLayoutManager(activity)
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
        val menuItem = menu.findItem(R.id.app_bar_search)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView = menuItem!!.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "enter blog's name"
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



