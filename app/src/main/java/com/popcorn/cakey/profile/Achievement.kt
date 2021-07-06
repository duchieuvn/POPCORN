package com.popcorn.cakey.profile

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.popcorn.cakey.R

class Achievement : AppCompatActivity() {
    private lateinit var atv: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.ac_fragment, AchieveFragment.newInstance())
                .commit()
        }

        atv = findViewById(R.id.autoText)



    }


}