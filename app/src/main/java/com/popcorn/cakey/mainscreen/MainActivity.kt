package com.popcorn.cakey.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.SplashActivity
import com.popcorn.cakey.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = ParseUser.getCurrentUser()
        if (currentUser == null) {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.mainToolbar)
        //  menuBar=findViewById(R.id.main_toolbar)
        //  setSupportActionBar(menuBar)

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
        return true
    }
}


