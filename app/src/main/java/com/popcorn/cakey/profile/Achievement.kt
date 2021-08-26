package com.popcorn.cakey.profile

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.UserModel
import com.popcorn.cakey.databinding.ActivityAchievementBinding

class Achievement : AppCompatActivity() {
    private lateinit var atv: AutoCompleteTextView

    //RUN WHEN STARTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        //Data binding
        val binding: ActivityAchievementBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_achievement)

        //Set adapter (list of values)
        //var arrayList = ArrayList<String>();
        //arrayList.add("Super")
        //arrayList.add("Pink")

        //DATA BINDING FROM SERVER
        var userModel = UserModel()
        var user = ParseUser.getCurrentUser()
        var achieve = userModel.getAchieList(user.getInt("level"))

        var queryAchieve = ParseQuery.getQuery<ParseObject>("Level")
        var arrayAdapter = ArrayAdapter<String>(applicationContext, R.layout.dropdown_item, achieve)
        binding.autoText.setAdapter(arrayAdapter)
        binding.autoText.threshold = 1

        //Preview button -->Show title, image and description
        binding.btPreview.setOnClickListener {
            //CURRENT ACHIEVEMENT SELECTED
            var current = binding.autoText.text.toString() //Achievement on parse sv
            //QUERY
            queryAchieve.whereEqualTo("achievement", current)
            var line = queryAchieve.find()[0]
            //BIND IMG
            val img = line?.getParseFile("icon")?.file
            if (img?.exists() == true) {
                val avatar = BitmapFactory.decodeFile(img.absolutePath)
                binding.imageView.setImageBitmap(avatar)
            } else
                binding.imageView.setImageResource(R.drawable.hi)
            //BIND DESCRIPTION
            binding.insertDescription = line.getString("description")


        }

        //Save changes button -->Send to server - SAVE CURRENT USER'S ACHIEVEMENT (BADGE)
        binding.btSaveChanges.setOnClickListener {
            var current2 = binding.autoText.text.toString()
            user.put("badge", current2)
            user.saveInBackground()
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
        }

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.profile)
        actionBar.setDisplayHomeAsUpEnabled(true)

    }


}