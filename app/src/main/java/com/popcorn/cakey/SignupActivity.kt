package com.popcorn.cakey

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.UserProfileChangeRequest
import com.popcorn.cakey.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var pwd: String
    private lateinit var cPwd: String
    // private lateinit var fireAuth: FirebaseAuth

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

        // fireAuth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            username = binding.etUsername.text.toString().trim()
            email = binding.etEmail.text.toString().trim()
            pwd = binding.etPassword.text.toString().trim()
            cPwd = binding.etConfirmPassword.text.toString().trim()
            // Check if any field is empty, validate email and confirm password
            when {
                TextUtils.isEmpty(username) -> {
                    Utils.showToast(this, R.string.username_required)
                }
                TextUtils.isEmpty(email) -> {
                    Utils.showToast(this, R.string.email_required)
                }
                (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> {
                    Utils.showToast(this, R.string.email_invalid)
                }
                TextUtils.isEmpty(pwd) -> {
                    Utils.showToast(this, R.string.password_required)
                }
                (pwd.length < resources.getInteger(R.integer.password_min_length)) -> {
                    Utils.showToast(this, R.string.password_too_short)
                }
                (pwd != cPwd) -> {
                    Utils.showToast(this, R.string.password_mismatch)
                }
                else -> {
                    Utils.blockInput(binding.progressBar, binding.btnSignup)
                    // fireAuth.createUserWithEmailAndPassword(email, pwd)
                    //     // Bind listener life cycle to this activity, to prevent activity leak
                    //     .addOnCompleteListener(this) { task ->
                    //         if (task.isSuccessful) {
                    //             val profileChange = UserProfileChangeRequest.Builder()
                    //                 .setDisplayName(username).build()
                    //             // Add username to Auth
                    //             fireAuth.currentUser!!.updateProfile(profileChange)
                    //             Utils.showToast(this, getString(R.string.auth_succeed, username))
                    //             // TODO: start mainActivity, currently waiting for UI design
                    //             finishAffinity()
                    //         } else
                    //             Utils.showToast(this, R.string.signup_failed)
                    //         Utils.unblockInput(binding.progressBar, binding.btnSignup)
                    //     }
                }
            }
        }
    }
}