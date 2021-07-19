package com.popcorn.cakey.blog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityReadBlogBinding

class ReadBlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityReadBlogBinding = DataBindingUtil.setContentView(this, R.layout.activity_read_blog)

        var defaultServing = 4
        binding.insertTitle = "Uong nuoc moi ngay"
        binding.insertAuthor = "Thanh Truc"
        binding.insertServings = defaultServing.toString() + " people"
        binding.authorAvatar.setImageResource(R.drawable.avatar)
        binding.blogCover.setImageResource(R.drawable.avatar)

        //Ingredients lists
        val ingredientsListView = binding.detailIngredient
            ingredientsListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val quantity = ArrayList<Int>()
        val unit = ArrayList<String>()
        val nameIngredient = ArrayList<String>()

        quantity.add(1000)
        quantity.add(500)

        unit.add("ml")
        unit.add("grams")

        nameIngredient.add("water")
        nameIngredient.add("pork")

        val ingredientsAdapter = IngredientsList(quantity,unit,nameIngredient)
        ingredientsListView.adapter = ingredientsAdapter

        //Guidelines
        val guidelinesView = binding.detailStep
        guidelinesView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val step = ArrayList<String>()

        step.add("rua tay")
        step.add("rua rau")
        step.add("lam banh mi")

        val guidelinesAdapter = Guidelines(step)
        guidelinesView.adapter = guidelinesAdapter

        //Ingredients calculator
        binding.calculateIngredient.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater

            val dialogLayout = inflater.inflate(R.layout.ingredients_calculator_dialog, null)

            val insertNumberServings  = dialogLayout.findViewById<TextInputEditText>(R.id.insertNumberServings)
            insertNumberServings.setHint(defaultServing.toString())

            builder.setView(dialogLayout)

            builder.setPositiveButton("OK") { dialogInterface, i ->
                if (!insertNumberServings.text.toString().equals(""))
                {
                    defaultServing = insertNumberServings.text.toString().toInt()

                    //reload ingredient
                    quantity.clear()
                    quantity.add(100)
                    quantity.add(500)

                    unit.clear()
                    unit.add("grams")
                    unit.add("grams")

                    nameIngredient.clear()
                    nameIngredient.add("sugar")
                    nameIngredient.add("salt")

                    ingredientsListView.adapter = ingredientsAdapter
                }

            }
            builder.setNegativeButton("CANCEL") {dialogInterface, i -> Int}

            builder.show()
        }
    }
}