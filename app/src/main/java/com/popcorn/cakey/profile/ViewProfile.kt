package com.popcorn.cakey.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityViewProfileBinding
import com.popcorn.cakey.auth.LoginActivity


class ViewProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_profile)

        var user = ParseUser.getCurrentUser()


        //Set user's data
        var premium: Int=-1
        var level = user.getInt("level")

        binding.insertID= user.objectId
        binding.insertName= user.getString("username")
        binding.insertMail= user.getString("email")
        binding.insertLevel= level.toString() + " ("+user.getInt("exp").toString()+"/100)"
        //create
        binding.insertTitle=user.getString("bagde")

        Log.d("ttt", binding.insertID + binding.insertName + binding.insertMail + binding.insertLevel + binding.insertTitle)
        Log.d("ttt", user.getString("expireDate").toString())

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
        binding.btLogout.setOnClickListener{
            ParseUser.logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }


    }
}