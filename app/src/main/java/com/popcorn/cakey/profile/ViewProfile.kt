package com.popcorn.cakey.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityViewProfileBinding

class ViewProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_profile)

        var user = ParseUser.getCurrentUser()

        //Set user's data
        var doge: Int=1
        var premium: Int=9
        binding.insertID= user.objectId
        binding.insertName= user.getString("username")
        binding.insertMail= user.getString("email")
        binding.insertLevel= user.getInt("level").toString()
        if (premium>0)
        {
            binding.insertPremium=premium.toString()
            if (premium == 1) binding.insertPremium+=" day left"
            else binding.insertPremium+=" days left"
        }
        else binding.insertPremium="Not activated"



        //Set buttons
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