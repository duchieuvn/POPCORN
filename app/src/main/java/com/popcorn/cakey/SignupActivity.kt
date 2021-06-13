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
            // Check if any field is empty
            when {
                TextUtils.isEmpty(binding.etUsername.text.toString().trim { it <= ' ' }) -> {
                    // Toast.makeText(this, getString(R.string.username_required), Toast.LENGTH_SHORT).show()
                    binding.etUsername.error = getString(R.string.username_required)
                    binding.etUsername.requestFocus()
                }

                TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                    // Toast.makeText(this, getString(R.string.email_required), Toast.LENGTH_SHORT).show()
                    binding.etEmail.error = getString(R.string.email_required)
                    binding.etEmail.requestFocus()
                }

                TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                    // Toast.makeText(this, getString(R.string.password_required), Toast.LENGTH_SHORT).show()
                    binding.etPassword.error = getString(R.string.password_required)
                    binding.etPassword.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches() -> {
                    binding.etEmail.error = getString(R.string.email_invalid)
                    binding.etEmail.requestFocus()
                }
            }
        }
    }
}