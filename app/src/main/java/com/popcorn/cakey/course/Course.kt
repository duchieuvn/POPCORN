package com.popcorn.cakey.course

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.parse.ParseObject
import com.parse.ParseQuery
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityCourseBinding


class Course : AppCompatActivity() {
    companion object {
        fun newInstance(): Course {
            return Course()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCourseBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_course)
        //DATA BINDING FROM DB
        val queryCourse = ParseQuery.getQuery<ParseObject>("Course")
        queryCourse.include("userID")

        // Used to click in main screen
        var value = String()
        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("ObjectId")!!
        }

        //SET ATTRIBUTES-text
        var course = queryCourse.get(value)
        var author = course.getParseUser("userID")
        binding.insertTitle = course.getString("title")
        binding.insertAuthor = author?.username.toString()
        binding.insertDescription = course.getString("description")

        //IMAGES - THEM ANH AUTHOR
        val avaFile = author?.getParseFile("avatar")?.file
        if (avaFile?.exists() == true) {
            val avatar = BitmapFactory.decodeFile(avaFile.absolutePath)
            binding.authorAvatar.setImageBitmap(avatar)
        } else
            binding.authorAvatar.setImageResource(R.drawable.avatar)


        //IMAGES - Course cover
        val avaImage = course?.getParseFile("img")?.file
        if (avaImage?.exists() == true) {
            val avatar = BitmapFactory.decodeFile(avaImage.absolutePath)
            binding.blogCover.setImageBitmap(avatar)
        } else
            binding.blogCover.setImageResource(R.drawable.hi)

        val youTubePlayerView = binding.youTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        // receive VIDEO LINK
        val video_link = course.getString("videoURL")

        //CHUA CONVERT LINK VIDEO -> ID
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = video_link
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        })


    }
}