package com.popcorn.cakey.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityReadBlogBinding
import com.popcorn.cakey.databinding.ViewIngredientBinding

class ReadBlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityReadBlogBinding = DataBindingUtil.setContentView(this, R.layout.activity_read_blog)

        binding.insertTitle = "Uong nuoc moi ngay"
        binding.insertAuthor = "Thanh Truc"
        binding.insertServings = "4"

        val numberIngredient = 1
        for (item in 1..numberIngredient)
        {
            val ingredient = layoutInflater.inflate(R.layout.view_ingredient, null)
            binding.detailIngredient.addView(ingredient)

            val insertQuantity = ingredient.findViewById<TextView>(R.id.insertQuantity)
            val insertUnit = ingredient.findViewById<TextView>(R.id.insertUnit)
            val insertName = ingredient.findViewById<TextView>(R.id.insertName)

            insertQuantity.text = "1000"
            insertUnit.text = "ml"
            insertName.text = "water"


        }



    }
}