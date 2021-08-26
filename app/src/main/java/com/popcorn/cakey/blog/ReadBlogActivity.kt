package com.popcorn.cakey.blog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.textfield.TextInputEditText
import com.parse.*
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityReadBlogBinding
import com.popcorn.cakey.report.ReportActivity
import java.io.File
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.roundToInt


class ReadBlogActivity : AppCompatActivity() {
    private var likeClick = true
    private var dislikeClick = true
    private var mPlayer: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private lateinit var binding: ActivityReadBlogBinding
    private lateinit var defaultDislike: String
    private lateinit var defaultLike: String
    private lateinit var playerView: PlayerView
    private lateinit var videoLink: String
    private lateinit var blog: ParseObject

    private val reactBlogCallback = FunctionCallback<Any?> { _, err ->
        if (err != null) {
            undo()
        }
    }

    private fun saveReport(blog: ParseObject, reason: String, callback: SaveCallback) {
        val report = ParseObject("Report")
        report.put("blog", blog)
        report.put("user", ParseUser.getCurrentUser())
        report.put("reason", reason)
        report.saveInBackground(callback)
    }

    companion object {
        fun newInstance(): ReadBlogActivity {
            return ReadBlogActivity()
        }
    }

    private val reportContact =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val reason = data?.getStringExtra("reason")!!
                /////////////////////// Report o day ///////////////////////////////
                saveReport(blog, reason) { e ->
                    if (e == null) {
                        // Report saved
                        Toast.makeText(this, reason, Toast.LENGTH_SHORT).show()
                    } else {
                        // Something went wrong, but I don't care
                        Toast.makeText(this, reason, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_read_blog)

        val queryBlog = ParseQuery.getQuery<ParseObject>("Blog")
        queryBlog.include("author").include("blogContent")
        var value = String()
        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("ObjectId")!!
        }

        blog = queryBlog.get(value)
        // Must guarantee that blogContent exist
        val blogContent = blog.getParseObject("blogContent")!!
        val author = blog.getParseUser("author")

        //Toolbar
        setSupportActionBar(binding.readBlogToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = blog.getString("name")

        //Author
        binding.insertAuthor = author?.getString("username")
        //Avatar
        val avaImage = author?.getParseFile("img")?.file
        if (avaImage?.exists() == true) {
            val avatar = BitmapFactory.decodeFile(avaImage.absolutePath)
            binding.authorAvatar.setImageBitmap(avatar)
        } else
            binding.authorAvatar.setImageResource(R.drawable.splash_screen)


        //Blog
        //Title
        binding.insertTitle = blog.getString("name")

        //Blog cover
        val coverImage = blog.getParseFile("img")?.file
        if (coverImage?.exists() == true) {
            val myBitmap = BitmapFactory.decodeFile(coverImage.absolutePath)
            binding.blogCover.setImageBitmap(myBitmap)
        }

        //Servings
        var defaultServing = blog.getInt("servings")
        binding.insertServings = "${blog.getInt("servings")} people"

        //Description
        binding.insertDescription = blog.getString("description")

        //Like & Dislike
        defaultLike = blog.getInt("like").toString()
        defaultDislike = blog.getInt("dislike").toString()
        binding.insertLike = defaultLike
        binding.insertDislike = defaultDislike


        //Ingredients lists
        val ingredientsListView = binding.detailIngredient
        ingredientsListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        val ingredients = blogContent.getJSONArray("ingredient")

        val quantity = ArrayList<Int>()
        val unit = ArrayList<String>()
        val nameIngredient = ArrayList<String>()


        if (ingredients != null) {
            for (i in 0 until ingredients.length()) {
                val item = ingredients.getJSONObject(i)
                val amount = item.getInt("amount")
                if (amount != 0 && item.has("name") && !item.isNull("name")) {
                    quantity.add(amount)
                    if (item.has("measurement") && !item.isNull("measurement"))
                        unit.add(item.getString("measurement").toString())
                    else unit.add("")

                    nameIngredient.add(item.getString("name").toString())
                }
            }
        }

        val ingredientsAdapter = IngredientsList(quantity, unit, nameIngredient)
        ingredientsListView.adapter = ingredientsAdapter

        //Guidelines
        val guidelinesView = binding.detailStep
        guidelinesView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val queryStep = ParseQuery.getQuery<ParseObject>("Step").include("blog")
        val stepList = queryStep.whereEqualTo("blog", blog).find()

        val stepName = ArrayList<String>()
        for (item in stepList) {
            stepName.add(item.getString("text").toString())
        }

        val guidelinesAdapter = Guidelines(stepName)
        guidelinesView.adapter = guidelinesAdapter

        //Ingredients calculator
        binding.calculateIngredient.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater

            val dialogLayout = inflater.inflate(R.layout.ingredients_calculator_dialog, null)

            val insertNumberServings =
                dialogLayout.findViewById<TextInputEditText>(R.id.insertNumberServings)
            insertNumberServings.hint = "$defaultServing people"

            builder.setView(dialogLayout)

            builder.setPositiveButton("OK") { _, _ ->
                if (insertNumberServings.text.toString() != "") {
                    if (insertNumberServings.text.toString() == "0")
                        Toast.makeText(this, "Invalid number!", Toast.LENGTH_SHORT).show()
                    else {
                        binding.insertServings = insertNumberServings.text.toString() + " people"

                        //reload ingredient
                        for (item in quantity) {
                            val amount = (item * (insertNumberServings.text.toString()
                                .toInt() / defaultServing.toFloat())).roundToInt()
                            if (amount != 0)
                                quantity[quantity.indexOf(item)] = amount
                            else
                                quantity[quantity.indexOf(item)] = 1
                        }

                        ingredientsListView.adapter = ingredientsAdapter

                        defaultServing = insertNumberServings.text.toString().toInt()
                    }

                }
            }
            builder.setNegativeButton("CANCEL") { _, _ -> Int }

            builder.show()
        }

        //////////////////////////////////////Tang like, dislike ////////////////////////////////////////////////////

        //Rating
        binding.like.setOnClickListener {
            like(blog)
        }


        binding.dislike.setOnClickListener {
            dislike(blog)
        }


        //Comment section
        val cmtView = binding.detailCmt
        cmtView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val listText = ArrayList<String>()
        val listUser = ArrayList<String>()
        val listImg = ArrayList<File>()

        val queryCmt = ParseQuery.getQuery<ParseObject>("Comment")
        queryCmt.include("blog").include("user")
        var cmt = queryCmt.whereEqualTo("blog", blog).setLimit(4).find()

        for (item in cmt){
            val user = item.getParseUser("user")

            listUser.add(user?.username.toString())
            listText.add(item.getString("text").toString())
            user?.getParseFile("avatar")?.file?.let { listImg.add(it) }
        }


        val cmtAdapter = CommentSection(listUser, listText, listImg)
        cmtView.adapter = cmtAdapter

        //User (viewer)
        val curUser = ParseUser.getCurrentUser()
        val ava = curUser.getParseFile("avatar")?.file
        if (ava?.exists() == true) {
            val myBitmap = BitmapFactory.decodeFile(ava.absolutePath)
            binding.userAvatar.setImageBitmap(myBitmap)
        }
        binding.insertUsername = curUser.username

        //If user leave a comment
        binding.sendButton.setOnClickListener {
            ////////////////////////////Update cai ten lai + push cmt///////////////////////////////////
            val text = binding.userDetailCmt.text.toString()
            listUser.add(curUser.username)
            listText.add(text)
            curUser?.getParseFile("avatar")?.file?.let { listImg.add(it) }

            cmtView.adapter = cmtAdapter
            binding.userDetailCmt.setText("")

            val comment = ParseObject("Comment")
            comment.put("text", text)
            comment.put("user", curUser)
            comment.put("blog", blog)
            comment.saveInBackground()

        }

        //Youtube
        videoLink = blogContent.getString("videoUrl")!!

        playerView = binding.playerView

    }

    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(this).build()
        // Bind the player to the view.
        playerView.player = mPlayer
        mPlayer!!.playWhenReady = true
        mPlayer!!.seekTo(playbackPosition)
        mPlayer!!.prepare(buildMediaSource(), false, false)
    }

    private fun buildMediaSource(): MediaSource {
        val userAgent =
            Util.getUserAgent(playerView.context, playerView.context.getString(R.string.app_name))

        val dataSourceFactory = DefaultHttpDataSourceFactory(userAgent)

        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(videoLink))

    }

    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        playWhenReady = mPlayer!!.playWhenReady
        playbackPosition = mPlayer!!.currentPosition
        currentWindow = mPlayer!!.currentWindowIndex
        mPlayer!!.release()
        mPlayer = null
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }


    private fun like(blog: ParseObject) {
        if (likeClick) {
            if (dislikeClick) {
                binding.insertLike = (defaultLike.toInt() + 1).toString()
                binding.like.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.pink_variant)
                likeClick = false
            }
            reactBlog(blog, true, reactBlogCallback)
        } else {
            binding.insertLike = defaultLike
            likeClick = true
            binding.like.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.pink)
            destroyReactBlog(blog, reactBlogCallback)
        }
    }

    private fun dislike(blog: ParseObject) {
        if (dislikeClick) {
            if (likeClick) {
                binding.insertDislike = (defaultDislike.toInt() + 1).toString()
                binding.dislike.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.pink_variant)
                dislikeClick = false

            }
            reactBlog(blog, false, reactBlogCallback)
        } else {
            binding.insertDislike = defaultDislike
            dislikeClick = true
            binding.dislike.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.pink)
            destroyReactBlog(blog, reactBlogCallback)
        }
    }

    private fun undo() {
        binding.dislike.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.pink)
        binding.insertDislike = defaultDislike

        binding.like.backgroundTintList =
            ContextCompat.getColorStateList(this, R.color.pink)
        binding.insertLike = defaultLike
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_report, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_report -> {
                val intent = Intent(this, ReportActivity::class.java)
                reportContact.launch(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reactBlog(blog: ParseObject, type: Boolean, callback: FunctionCallback<Any?>) {
        val params = HashMap<String, Any>()
        params["blogId"] = blog.objectId
        params["type"] = type
        ParseCloud.callFunctionInBackground("reactBlog", params, callback)
    }

    private fun destroyReactBlog(blog: ParseObject, callback: FunctionCallback<Any?>) {
        val params = HashMap<String, Any>()
        params["blogId"] = blog.objectId
        ParseCloud.callFunctionInBackground("destroyReactBlog", params, callback)
    }
}

