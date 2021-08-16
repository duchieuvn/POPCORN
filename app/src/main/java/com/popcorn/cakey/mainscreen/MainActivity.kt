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
import com.popcorn.cakey.blog.Course
import com.popcorn.cakey.blog.WriteBlogActivity
import com.popcorn.cakey.databinding.ActivityMainBinding
import com.popcorn.cakey.profile.ViewProfile

class MainActivity : AppCompatActivity(R.layout.activity_main)  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigation: NavigationView

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
        //  menuBar=findViewById(R.id.main_toolbar)
        //  setSupportActionBar(menuBar)
        navigation=findViewById(R.id.nav_view)
        navigation.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            val i=intent
            when(it.itemId){
                R.id.Course->{
                    i.setClass(this,Course::class.java)
                    startActivity(i)
                    true
                }
                R.id.Write->{
                    i.setClass(this,WriteBlogActivity::class.java)
                    startActivity(i)
                    true
                }
            }
            true
        })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.one_fragment, Fragment1Activity.newInstance())
                .add(R.id.two_fragment, Fragment2Activity.newInstance())
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (menu != null) {
            (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
            }
        }
        return true
    }

}


