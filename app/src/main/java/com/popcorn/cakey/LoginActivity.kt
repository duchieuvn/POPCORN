package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.popcorn.cakey.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.login)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}