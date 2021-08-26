package com.popcorn.cakey.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.ParseFile
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityEditProfileBinding
import com.popcorn.cakey.databinding.ActivityViewProfileBinding
import java.io.ByteArrayOutputStream

class EditProfile : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var avatar: ImageView
    private lateinit var name: EditText
    private lateinit var mail: EditText

    //TO LOAD IMG FROM PHONE
    private val startForAvatar =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                val uri: Uri = data?.data!!

                avatar.setImageURI(uri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    //RUN WHEN STARTED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val binding: ActivityEditProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile)

        //Get user object from server
        var user = ParseUser.getCurrentUser()

        //Bind user's attributes
        //Text attribtues
        binding.insertID= user.objectId
        binding.insertName= user.getString("username")
        binding.insertMail= user.getString("email")
        binding.insertPassword= user.getString("password")

        //Current avatar - Image attributes
        val avaImage = user?.getParseFile("avatar")?.file
        if (avaImage?.exists() == true) {
            val avatar = BitmapFactory.decodeFile(avaImage.absolutePath)
            binding.avatarImg.setImageBitmap(avatar)
        } else
            binding.avatarImg.setImageResource(R.drawable.splash_screen)


        //Change avatar load from phone
        avatar = findViewById(R.id.avatarImg);
        fab = findViewById(R.id.Addbutton);

        fab.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)   //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForAvatar.launch(intent)
                }
        }

        //Set button - send info to parse Server
        binding.btSubmitChanges.setOnClickListener {

            // PA bind lai email o day <----------
            name = findViewById(R.id.Name_IP)
            mail = findViewById(R.id.Email_IP)
            var username = name.text.toString()
            var email = mail.text.toString()

            user.put("email",binding.insertMail.toString())
            user.put("username", username)

            //Check password and set
//            var password=binding.insertPassword.toString()
//            if (password!=null) user.setPassword(password)


            if (avatar.drawable != null) {
                val drawable = avatar.drawable as BitmapDrawable
                val bitmap = drawable.bitmap

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val data: ByteArray = stream.toByteArray()
                var filename = username + ".jpeg"
                val file = ParseFile(filename, data)

                user.put("avatar", file)
            }
            user.saveInBackground()
            //Announce
            Toast.makeText(this, "Submitted", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ViewProfile::class.java)
            startActivity(intent)
        }

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.profile)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

}