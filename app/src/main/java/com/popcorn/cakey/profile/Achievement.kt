package com.popcorn.cakey.profile

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityAchievementBinding

class Achievement : AppCompatActivity() {
    private lateinit var atv: AutoCompleteTextView

    //RUN WHEN STARTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        //Data binding
        val binding: ActivityAchievementBinding = DataBindingUtil.setContentView(this,R.layout.activity_achievement)

        //Set adapter (list of values)
        var arrayList = ArrayList<String>();
        arrayList.add("Super")
        arrayList.add("Pink")

        //DATA BINDING FROM SERVER
        //var userModel = UserModel()
        //var user= ParseUser.getCurrentUser()
        // get 1 line: var objectLevel = userModel.getLevel(1)
        //Get 1 list
        //var achi = userModel.getAchieList(user.getInt("level"))


        var arrayAdapter= ArrayAdapter<String>(applicationContext,R.layout.dropdown_item,arrayList)
        binding.autoText.setAdapter(arrayAdapter)
        binding.autoText.threshold=1

        //Preview button -->Show title, image and description
        binding.btPreview.setOnClickListener {
            var achieve=binding.autoText.text
            binding.imageView.setImageResource(R.drawable.hi)
            binding.insertDescription="Đây là danh hiệu giành cho pé Lu, SKT vô địch"
            Toast.makeText(this, achieve, Toast.LENGTH_SHORT).show()

        }

        //Save changes button -->Send to server
        binding.btSaveChanges.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
        }

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.profile)
        actionBar.setDisplayHomeAsUpEnabled(true)

    }


}