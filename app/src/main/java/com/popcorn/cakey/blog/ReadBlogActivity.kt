package com.popcorn.cakey.blog




import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityReadBlogBinding
import com.popcorn.cakey.report.ReportActivity
import com.parse.ParseQuery

import com.parse.ParseObject
import java.lang.Math.round
import kotlin.math.roundToInt

class ReadBlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityReadBlogBinding = DataBindingUtil.setContentView(this, R.layout.activity_read_blog)

        val queryBlog = ParseQuery.getQuery<ParseObject>("Blog")
        queryBlog.include("author")
        val blog = queryBlog.get("STbJ0W7Dtq")
        val author = blog.getParseUser("author")

        //Toolbar
        setSupportActionBar(binding.readBlogToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = blog.getString("name")

        //Author
        binding.insertAuthor = author?.getString("username")
        //Avatar
        val avaImage = author?.getParseFile("img")?.file

        if (avaImage?.exists() == true)
        {
            val avatar = BitmapFactory.decodeFile(avaImage.absolutePath)
            binding.authorAvatar.setImageBitmap(avatar)
        }
        else
            binding.authorAvatar.setImageResource(R.drawable.splash_screen)


        //Blog
        //Title
        binding.insertTitle = blog.getString("name")

        //Blog cover
        val coverImage = blog.getParseFile("img")?.file

        if (coverImage?.exists() == true)
        {
            val myBitmap = BitmapFactory.decodeFile(coverImage.absolutePath)
            binding.blogCover.setImageBitmap(myBitmap)
        }

        //Servings
        var defaultServing = blog.getInt("servings")
        binding.insertServings = "${blog.getInt("servings")} people"


        //Description

        binding.authorAvatar.setImageResource(R.drawable.avatar)
        binding.blogCover.setImageResource(R.drawable.hi)

        binding.blogCover.setImageResource(R.drawable.avatar)
        binding.userAvatar.setImageResource(R.drawable.avatar)

        binding.insertDescription = blog.getString("description")

        //Like & Dislike
        binding.insertLike = blog.getInt("like").toString()
        binding.insertDislike = blog.getInt("dislike").toString()

        //User (viewer)
        binding.insertUsername = "Duc Hieu"
        binding.userAvatar.setImageResource(R.drawable.avatar)

        //Ingredients lists
        val ingredientsListView = binding.detailIngredient
            ingredientsListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val queryIngreList = ParseQuery.getQuery<ParseObject>("Ingredient").include("blog")
        queryIngreList.whereEqualTo("blog", blog)

        val ingreList = queryIngreList.find()

        var quantity = ArrayList<Int>()
        var unit = ArrayList<String>()
        var nameIngredient = ArrayList<String>()

        for (item in ingreList){
            val amount = item.getInt("amount")
            if (amount != 0 && item.getString("name") != null)
            {
                quantity.add(amount)
                if (item.getString("measurement") == null)
                    unit.add("")
                else unit.add(item.getString("measurement").toString())

                nameIngredient.add(item.getString("name").toString())
            }

        }

        val ingredientsAdapter = IngredientsList(quantity,unit,nameIngredient)
        ingredientsListView.adapter = ingredientsAdapter

        //Guidelines
        val guidelinesView = binding.detailStep
        guidelinesView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val queryStep = ParseQuery.getQuery<ParseObject>("Step").include("blog")
        val stepList = queryStep.whereEqualTo("blog", blog).find()

        val stepName = ArrayList<String>()
        for (item in stepList){
            stepName.add(item.getString("text").toString())
        }

        val guidelinesAdapter = Guidelines(stepName)
        guidelinesView.adapter = guidelinesAdapter

        //Ingredients calculator
        binding.calculateIngredient.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater

            val dialogLayout = inflater.inflate(R.layout.ingredients_calculator_dialog, null)

            val insertNumberServings  = dialogLayout.findViewById<TextInputEditText>(R.id.insertNumberServings)
            insertNumberServings.hint = "$defaultServing people"

            builder.setView(dialogLayout)

            builder.setPositiveButton("OK") { _, _ ->
                if (insertNumberServings.text.toString() != "")
                {
                    binding.insertServings = insertNumberServings.text.toString() + " people"

                    //reload ingredient
                    for (item in quantity)
                        quantity[quantity.indexOf(item)] =
                            (item * ((insertNumberServings.text.toString()
                                .toInt()) / defaultServing.toFloat())).roundToInt()

                    //ingredientsAdapter = IngredientsList(quantity,unit,nameIngredient)
                    ingredientsListView.adapter = ingredientsAdapter
                }
                defaultServing = insertNumberServings.text.toString().toInt()
            }
            builder.setNegativeButton("CANCEL") { _, _ -> Int}

            builder.show()
        }

        //////////////////////////////////////Tang like, dislike ////////////////////////////////////////////////////
        var likeClick = true
        var dislikeClick = true
        //Rating
        binding.like.setOnClickListener {
            if (likeClick)
            {
                if (dislikeClick)
                {
                    //binding.insertLike = (binding.insertLike.toInt()+1).toString()
                    binding.like.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink_variant)
                    likeClick = false
                }

            }
            else
            {
                //binding.insertLike = (binding.insertLike.toInt()-1).toString()
                likeClick = true
                binding.like.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink)
            }
        }

        binding.dislike.setOnClickListener {
            if (dislikeClick)
            {
                if (likeClick) {
                    //binding.insertDislike = (binding.insertDislike.toInt()+1).toString()
                    binding.dislike.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink_variant)
                    dislikeClick = false
                }
            }
            else
            {
                //binding.insertDislike = (binding.insertDislike.toInt()-1).toString()
                dislikeClick = true
                binding.dislike.backgroundTintList = ContextCompat.getColorStateList(this,R.color.pink)
            }
        }

        //Comment section
        val cmtView = binding.detailCmt
        cmtView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val cmt = ArrayList<String>()
        cmt.add("Ngon qua")
        cmt.add("Hay qua")

        val user = ArrayList<String>()
        user.add("clone1")
        user.add("clone2")

        val cmtAdapter = CommentSection(user,cmt)
        cmtView.adapter = cmtAdapter

        //If user leave a comment
        binding.sendButton.setOnClickListener {
            ////////////////////////////Update cai ten lai + push cmt///////////////////////////////////
            user.add("Duc Hieu")
            cmt.add(binding.userDetailCmt.text.toString())
            cmtView.adapter = cmtAdapter
            binding.userDetailCmt.setText("")
        }

        //Report
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            val reason = intent.getStringExtra("reason")
            Toast.makeText(this,reason, Toast.LENGTH_SHORT).show()
            //Gui report len admin o day
        }

        //Youtube
        val youTubePlayerView = binding.youTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "7VTtenyKRg4"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_report, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) : Boolean{
        when (item.itemId) {
            R.id.app_bar_report -> {
                val intent = Intent(this, ReportActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

