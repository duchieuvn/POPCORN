package com.popcorn.cakey

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity: AppCompatActivity(R.layout.activity_main) {
    private lateinit var menuBar: androidx.appcompat.widget.Toolbar
    private lateinit var f1: Fragment1Activity
    private lateinit var f2: Fragment2Activity
    private  lateinit var fm: FragmentManager
    private  lateinit var ft: FragmentTransaction

    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuBar=findViewById(R.id.main_toolbar)
        setSupportActionBar(menuBar)

        if (savedInstanceState==null)
        {
            supportFragmentManager.beginTransaction().commit()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater= menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }
}


