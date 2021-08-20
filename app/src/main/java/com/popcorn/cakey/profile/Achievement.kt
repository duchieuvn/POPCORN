package com.popcorn.cakey.profile

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.UserModel
import com.popcorn.cakey.databinding.ActivityAchievementBinding
import com.popcorn.cakey.databinding.ActivityEditProfileBinding

class Achievement : AppCompatActivity() {
    private lateinit var atv: AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        //Data binding
        val binding: ActivityAchievementBinding = DataBindingUtil.setContentView(this,R.layout.activity_achievement)

        //Set value - adapter
        var arrayList = ArrayList<String>();
        arrayList.add("Super")
        arrayList.add("Pink")

        //var userModel = UserModel()
        //var user= ParseUser.getCurrentUser()
        // get 1 line: var objectLevel = userModel.getLevel(1)
        //Get 1 list
        //var achi = userModel.getAchieList(user.getInt("level"))



        var arrayAdapter= ArrayAdapter<String>(applicationContext,R.layout.dropdown_item,arrayList)
        binding.autoText.setAdapter(arrayAdapter)
        binding.autoText.threshold=1

        //Set buttons
        binding.btReview.setOnClickListener {
            var achieve=binding.autoText.text
            binding.imageView.setImageResource(R.drawable.hi)
            binding.insertDescription="Đây là danh hiệu giành cho pé Lu, SKT vô địch"
            Toast.makeText(this, achieve, Toast.LENGTH_SHORT).show()

        }
        binding.btSaveChanges.setOnClickListener {
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()
        }
       /* if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.ac_fragment, AchieveFragment.newInstance())
                .commit()
        }

        atv = findViewById(R.id.autoText)
*/
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.profile)
        actionBar.setDisplayHomeAsUpEnabled(true)

    }


}