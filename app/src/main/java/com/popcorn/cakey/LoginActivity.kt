package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.popcorn.cakey.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var pwd: String
    private lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.login)
        actionBar.setDisplayHomeAsUpEnabled(true)

        fireAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            email = binding.etEmail.text.toString().trim()
            pwd = binding.etPassword.text.toString().trim()
            when {
                TextUtils.isEmpty(email) -> {
                    Utils.showToast(this, R.string.email_required)
                }
                TextUtils.isEmpty(pwd) -> {
                    Utils.showToast(this, R.string.password_required)
                }
                else -> {
                    blockLogin()
                    fireAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val username = fireAuth.currentUser!!.displayName
                                Utils.showToast(this, getString(R.string.auth_succeed, username))
                                // TODO: start mainActivity, currently waiting for UI design
                                finishAffinity()
                            } else
                                binding.loginError.text = getString(R.string.login_failed)
                            unblockLogin()
                        }
                }
            }
        }
    }

    private fun blockLogin() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.isEnabled = false
    }

    private fun unblockLogin() {
        binding.progressBar.visibility = View.GONE
        binding.btnLogin.isEnabled = true
    }
}