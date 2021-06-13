package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.popcorn.cakey.databinding.ActivitySignupBinding

data class User(val username: String, val email: String)

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private lateinit var fireAuth: FirebaseAuth

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

        fireAuth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val pwd = binding.etPassword.text.toString().trim()
            val cPwd = binding.etConfirmPassword.text.toString().trim()
            // Check if any field is empty, validate email and confirm password
            when {
                TextUtils.isEmpty(username) -> {
                    showToast(R.string.username_required)
                }

                TextUtils.isEmpty(email) -> {
                    showToast(R.string.email_required)
                }

                (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
                    showToast(R.string.email_invalid)
                }

                TextUtils.isEmpty(pwd) -> {
                    showToast(R.string.password_required)
                }

                (pwd.length < resources.getInteger(R.integer.password_min_length)) -> {
                    showToast(R.string.password_too_short)
                }

                (pwd != cPwd) -> {
                    showToast(R.string.password_mismatch)
                }

                else -> {
                    binding.progressBar.visibility = View.VISIBLE
                    fireAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(this) { taskAuth ->
                            if (taskAuth.isSuccessful) {
                                val user = User(username, email)
                                val uid = fireAuth.currentUser!!.uid

                                FirebaseDatabase.getInstance().getReference("Users")
                                    .child(uid).setValue(user)
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            showToast(getString(R.string.signup_succeed, username))
                                        } else {
                                            showToast(R.string.signup_failed)
                                        }
                                    }
                            }
                            binding.progressBar.visibility = View.GONE
                        }
                }
            }
        }
    }

    private fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(text: CharSequence) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}