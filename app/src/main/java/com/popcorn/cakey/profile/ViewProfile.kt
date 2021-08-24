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
    //RUN WHEN STARTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityViewProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_view_profile)

        //Get user object from server
        var user = ParseUser.getCurrentUser()


        //Bind user's attributes
        var premium: Int=-1
        var level = user.getInt("level")

        //Text attributes
        binding.insertID= user.objectId
        binding.insertName= user.getString("username")
        binding.insertMail= user.getString("email")
        binding.insertLevel= level.toString() + " ("+user.getInt("exp").toString()+"/100)"
        binding.insertTitle=user.getString("bagde")
        //Avatar - image attributes
        //GET IMAGE HERE
        binding.profileImage.setImageResource(R.drawable.hi)

        //Check premium value
        if (premium>0)
        {
            binding.insertPremium=premium.toString()
            if (premium == 1) binding.insertPremium+=" day left"
            else binding.insertPremium+=" days left"
        }
        else binding.insertPremium="Not activated"

        //Set buttons' listener
        //Edit profile button -->Go to edit profile
        binding.btEditprofile.setOnClickListener{
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        //Change achievement button -->Go to change achievement
        binding.btAchieve.setOnClickListener {
            val intent = Intent(this,Achievement::class.java)
            startActivity(intent)

        }

        //Log out button -->Log out, close the application
        binding.btLogout.setOnClickListener{
            ParseUser.logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }


    }
}