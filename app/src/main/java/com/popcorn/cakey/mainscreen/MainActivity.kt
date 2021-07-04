package com.popcorn.cakey.mainscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBar: androidx.appcompat.widget.Toolbar


    @SuppressLint("UseSupportActionBar", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //val view = binding.root
       // setContentView(view)
        menuBar=findViewById(R.id.main_toolbar)
        setSupportActionBar(menuBar)
        //setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState==null)
        {
            supportFragmentManager.beginTransaction()
                .add(R.id.one_fragment, Fragment1Activity.newInstance())
                .add(R.id.two_fragment, Fragment2Activity.newInstance())
                .commit()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater= menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }
}


