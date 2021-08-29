package com.popcorn.cakey.course

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityCourseMenuBinding
import com.popcorn.cakey.mainscreen.BlogListActivity
import java.io.ByteArrayOutputStream

data class ACourse(var title: String, var author: String, var img: ParseFile, var ID: String)

class CourseMenu : AppCompatActivity(R.layout.activity_course_menu) {
    private lateinit var courseArrayList: ArrayList<ACourse>
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var titleID: ArrayList<String>
    private lateinit var imageID: ArrayList<ParseFile>
    private lateinit var authorID: ArrayList<String>
    private val mew = R.drawable.avatar
    private lateinit var nameID: ArrayList<String>

    private var binding: ActivityCourseMenuBinding? = null

    @SuppressLint("WrongThread")
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityCourseMenuBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // null picture
        val icon = BitmapFactory.decodeResource(resources, mew)
        val stream = ByteArrayOutputStream()
        icon.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byte = stream.toByteArray()
        val temp = ParseFile(byte)

        val queryBlog = ParseQuery.getQuery<ParseObject>("Course").setLimit(5)
        val data = queryBlog?.find()
        titleID = ArrayList()
        imageID = ArrayList()
        authorID = ArrayList()
        nameID = ArrayList()

        //Bind data
        courseArrayList = ArrayList()
        for (i in data?.indices!!) {
            val pics = data[i].getParseFile("img")
            var name = data[i].getParseUser("userID")?.fetchIfNeeded()?.get("username")
            if (name == null) {
                name = "Example Author"
            }
            titleID.add(data[i].getString("title").toString())
            authorID.add(name as String)
            if (pics != null) {
                imageID.add(pics)
            } else {
                imageID.add(temp)
            }
            nameID.add(data[i].objectId)
        }
        getData()
        val view = binding!!.root

        layoutManager= LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
        recyclerView=view.findViewById(R.id.CourseList)
        recyclerView.layoutManager=layoutManager

        recyclerView.adapter= CourseAdapter(courseArrayList)


    }

    private fun getData() {
        courseArrayList = arrayListOf()
        for (i in titleID.indices) {
            val course = ACourse(titleID[i], authorID[i], imageID[i], nameID[i])
            courseArrayList.add(course)
        }
    }

}





