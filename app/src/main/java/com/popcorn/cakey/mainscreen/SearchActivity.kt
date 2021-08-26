package com.popcorn.cakey.mainscreen

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import java.io.ByteArrayOutputStream

class SearchActivity: Activity() {
    private lateinit var query: ParseQuery<ParseObject>
    private lateinit var title: ArrayList<String>
    private lateinit var id: ArrayList<String>
    companion object{
        fun newInstance(): SearchActivity {
            return SearchActivity()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val input = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            query.limit = 5
            query= ParseQuery.getQuery("blog")
            query.whereContains("name",input)


        }
    }
    /*private fun queryData(){
        val queryBlog= ParseQuery.getQuery<ParseObject>("Blog").setLimit(10)
        val data = queryBlog?.find()
        title=ArrayList()
        id= ArrayList()
        for (i in data?.indices!!){
            val pics=data[i].getParseFile("img")
            id.add(data[i].objectId)


        }
    }*/


}