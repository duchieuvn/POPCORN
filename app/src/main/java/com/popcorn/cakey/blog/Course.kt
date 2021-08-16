package com.popcorn.cakey.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityCourseBinding
import com.popcorn.cakey.databinding.ActivityReadBlogBinding

class Course : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCourseBinding = DataBindingUtil.setContentView(this, R.layout.activity_course)

        binding.insertTitle = "Tiramisu"
        binding.insertAuthor = "Thanh Truc"
        binding.insertLike="1904"
        binding.insertDislike="2001"
        binding.insertDescription="tao viet khugn dien netao viet khugn dien netao viet khugn dien netao viet khugn dien netao viet khugn dien netao viet khugn dien netao viet khugn dien ne"
        binding.authorAvatar.setImageResource(R.drawable.avatar)
        binding.blogCover.setImageResource(R.drawable.avatar)
        var likeClick = true
        var dislikeClick = true
        //Rating
        binding.like.setOnClickListener {
            if (likeClick)
            {
                if (dislikeClick)
                {
                    binding.like.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink_variant)
                    likeClick = false
                }

            }
            else
            {
                likeClick = true
                binding.like.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink)
            }
        }

        binding.dislike.setOnClickListener {
            if (dislikeClick)
            {
                if (likeClick) {
                    binding.dislike.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink_variant)
                    dislikeClick = false
                }
            }
            else
            {
                dislikeClick = true
                binding.dislike.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink)
            }
        }
    }
}