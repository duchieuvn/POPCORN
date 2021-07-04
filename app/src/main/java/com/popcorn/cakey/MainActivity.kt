package com.popcorn.cakey

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity(R.layout.activity_main) {
    private lateinit var menuBar: androidx.appcompat.widget.Toolbar


    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menuBar=findViewById(R.id.main_toolbar)
        setSupportActionBar(menuBar)

        if (savedInstanceState==null)
        {
            supportFragmentManager.beginTransaction()
                .add(R.id.one_fragment,Fragment1Activity.newInstance())
                .add(R.id.two_fragment,Fragment2Activity.newInstance())
                .commit()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater= menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }
}


