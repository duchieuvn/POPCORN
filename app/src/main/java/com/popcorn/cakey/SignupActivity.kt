package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.popcorn.cakey.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    // private lateinit var FirebaseAuth fireAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // setContentView(R.layout.activity_signup)

        // Create toolbar and back button
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.signup)
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.btnSignup.setOnClickListener {
            val username = binding.etUsername
            val email = binding.etEmail
            val pwd = binding.etPassword
            val cpwd = binding.etConfirmPassword
            // Check if any field is empty, validate email and confirm password
            when {
                TextUtils.isEmpty(username.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, getString(R.string.username_required), Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, getString(R.string.email_required), Toast.LENGTH_SHORT).show()
                }

                (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) -> {
                    Toast.makeText(this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(pwd.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, getString(R.string.password_required), Toast.LENGTH_SHORT).show()
                }

                (pwd.length() < resources.getInteger(R.integer.password_min_length)) -> {
                    Toast.makeText(this, getString(R.string.password_too_short), Toast.LENGTH_SHORT).show()
                }
                // TODO: Fix compare string
                (pwd.text != cpwd.text) -> {
                    Toast.makeText(this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}