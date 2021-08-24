package com.popcorn.cakey.blog

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.parse.ParseObject
import com.parse.ParseQuery
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityCourseBinding


class Course : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCourseBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_course)
        //DATA BINDING FROM DB
        val queryCourse = ParseQuery.getQuery<ParseObject>("Course")
        queryCourse.include("userID")

        //SET ATTRIBUTES-text
        var course = queryCourse.get("UYk0ofeGYE")
        var author = course.get("userID")
        binding.insertTitle = course.getString("title")
        binding.insertAuthor = "Thanh Truc" //CHUA CO
        binding.insertLike = course.getInt("like").toString()
        binding.insertDislike = course.getInt("dislike").toString()
        binding.insertDescription = course.getString("description")

        //IMAGES - THEM ANH AUTHOR
        binding.authorAvatar.setImageResource(R.drawable.avatar) //chua

        //IMAGES - Course cover
        val avaImage = course?.getParseFile("img")?.file
        if (avaImage?.exists() == true) {
            val avatar = BitmapFactory.decodeFile(avaImage.absolutePath)
            binding.blogCover.setImageBitmap(avatar)
        } else
            binding.blogCover.setImageResource(R.drawable.hi)

        val youTubePlayerView = binding.youTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        //CHUA BO LINK VIDEO
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "orJSJGHjBLI"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

        var likeClick = true
        var dislikeClick = true
        //Rating
        binding.like.setOnClickListener {
            if (likeClick) {
                if (dislikeClick) {
                    binding.like.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.pink_variant)
                    likeClick = false
                }

            } else {
                likeClick = true
                binding.like.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.pink)
            }
        }

        binding.dislike.setOnClickListener {
            if (dislikeClick) {
                if (likeClick) {
                    binding.dislike.backgroundTintList =
                        ContextCompat.getColorStateList(this, R.color.pink_variant)
                    dislikeClick = false
                }
            } else {
                dislikeClick = true
                binding.dislike.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.pink)
            }
        }
    }
}