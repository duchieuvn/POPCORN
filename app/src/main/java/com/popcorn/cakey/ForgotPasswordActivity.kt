package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
// import com.google.firebase.auth.FirebaseAuth
import com.popcorn.cakey.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var email: String
    // private lateinit var fireAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // setContentView(R.layout.activity_forgot_password)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.forgot_password)
        actionBar.setDisplayHomeAsUpEnabled(true)

        // fireAuth = FirebaseAuth.getInstance()

        binding.btnSubmit.setOnClickListener {
            // email = binding.etEmail.text.toString().trim()
            // if (TextUtils.isEmpty(email))
            //     Utils.showToast(this, R.string.email_required)
            // else {
            //     fireAuth.sendPasswordResetEmail(email)
            //     finish()
            // }
        }
    }
}