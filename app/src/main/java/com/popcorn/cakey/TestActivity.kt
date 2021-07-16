package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseObject
import com.parse.ParseUser

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var user = ParseUser.getCurrentUser()

        //Log.d("t-t", user)

        var blog = ParseObject("Blog")
        blog.put("userID", user)
        blog.put("Title", "New Title")
        blog.saveInBackground()
    }
}