package com.popcorn.cakey.auth

// import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.Utils
import com.popcorn.cakey.blog.WriteBlogActivity

import com.popcorn.cakey.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var username: String
    private lateinit var pwd: String
    // private lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // fireAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            username = binding.etEmail.text.toString().trim()
            pwd = binding.etPassword.text.toString().trim()
            when {
                TextUtils.isEmpty(username) -> {
                    Utils.showToast(this, R.string.username_required)
                }
                TextUtils.isEmpty(pwd) -> {
                    Utils.showToast(this, R.string.password_required)
                }
                else -> {
                    Utils.blockInput(binding.progressBar, binding.btnLogin)
                    ParseUser.logInInBackground(username, pwd) { user, e ->
                        if (user != null) {
                            Utils.showToast(this, getString(R.string.auth_succeed, user.email))
                            val intent = Intent(this, WriteBlogActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        } else {
                            // Signup failed. Look at the ParseException to see what happened.
                            binding.loginError.text = getString(R.string.login_failed)
                            Utils.unblockInput(binding.progressBar, binding.btnLogin)
                        }
                    }
                    // fireAuth.signInWithEmailAndPassword(email, pwd)
                    //     .addOnCompleteListener(this) { task ->
                    //         if (task.isSuccessful) {
                    //             val username = fireAuth.currentUser!!.displayName
                    //             Utils.showToast(this, getString(R.string.auth_succeed, username))
                    //             //start mainActivity, currently waiting for UI design
                    //             finishAffinity()
                    //         } else
                    //             binding.loginError.text = getString(R.string.login_failed)
                    //         Utils.unblockInput(binding.progressBar, binding.btnLogin)
                    //     }
                }
            }
        }

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}