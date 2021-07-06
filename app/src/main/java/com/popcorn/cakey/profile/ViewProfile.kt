package com.popcorn.cakey.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.popcorn.cakey.R
import com.popcorn.cakey.profile.EditProfile

class ViewProfile : AppCompatActivity() {
    private lateinit var bt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        bt = findViewById(R.id.view_profile_achieve);
    }
}

