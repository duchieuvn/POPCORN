package com.popcorn.cakey.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.popcorn.cakey.ForgotPasswordActivity
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityViewProfileBinding

class ViewProfile : AppCompatActivity() {
    private lateinit var bt: Button
    private var inputText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_profile)

        binding.insertID="1"

        binding.btEditprofile.setOnClickListener{
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        binding.btAchieve.setOnClickListener {
            val intent = Intent(this,Achievement::class.java)
            startActivity(intent)
        }



    }
}

