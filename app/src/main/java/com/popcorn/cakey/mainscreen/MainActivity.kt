package com.popcorn.cakey.mainscreen

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.SplashActivity
import com.popcorn.cakey.blog.WriteBlogActivity
import com.popcorn.cakey.course.CourseMenu
import com.popcorn.cakey.databinding.ActivityMainBinding
import com.popcorn.cakey.faqs.FAQsActivity
import com.popcorn.cakey.profile.ViewProfile

class MainActivity : AppCompatActivity(R.layout.activity_main){

    private lateinit var binding: ActivityMainBinding
    //private lateinit var bloglist: ArrayList<BlogThumbnails>
    private lateinit var navigation: NavigationView
    //private  var itemAdapter: BlogListActivity?=null
    /*private lateinit var title: ArrayList<String>
    private lateinit var image: ArrayList<ParseFile>
    private lateinit var author: ArrayList<String>
    private val mew=R.drawable.avatar
    private lateinit var blogid: ArrayList<String>*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = ParseUser.getCurrentUser()
        if (currentUser == null) {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }



        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.mainToolbar)

        navigation=findViewById(R.id.nav_view)
        navigation.setNavigationItemSelectedListener {
            val i = intent
            when (it.itemId) {
                R.id.Course -> {
                    //i.setClass(this, Course::class.java)
                    i.setClass(this, CourseMenu::class.java)
                    startActivity(i)

                }
                R.id.Write -> {
                    i.setClass(this, WriteBlogActivity::class.java)
                    startActivity(i)

                }
                R.id.Account -> {
                    i.setClass(this, ViewProfile::class.java)
                    startActivity(i)
                }
                R.id.Help -> {
                    i.setClass(this, FAQsActivity::class.java)
                    startActivity(i)
                }
            }
            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.one_fragment, SuggestionFragment.newInstance())
                .add(R.id.two_fragment, BlogListFragment.newInstance())
                .commit()
           /* supportFragmentManager.setFragmentResultListener("requestKey",this)
            { _, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.get("blogs")
                itemAdapter= result as BlogListActivity?

            }*/
        }


    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (menu != null) {
            (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
                // Assumes current activity is the searchable activity
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                isIconifiedByDefault = false // Do not iconify the widget; expand it by default
            }
        }
        return true

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }*/



}


