package com.popcorn.cakey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.popcorn.cakey.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var pwd: String
    private lateinit var cPwd: String
    private lateinit var fireAuth: FirebaseAuth
    private lateinit var fireDB: FirebaseDatabase

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
        fireDB = FirebaseDatabase.getInstance()

        binding.btnSignup.setOnClickListener {
            username = binding.etUsername.text.toString().trim()
            email = binding.etEmail.text.toString().trim()
            pwd = binding.etPassword.text.toString().trim()
            cPwd = binding.etConfirmPassword.text.toString().trim()
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
                    blockSignup()
                    fireAuth.createUserWithEmailAndPassword(email, pwd)
                        // Bind listener life cycle to this activity, to prevent activity leak
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                val profileChange = UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build()
                                fireAuth.currentUser!!.updateProfile(profileChange)
                                    .addOnSuccessListener(this) {
                                        showToast(getString(R.string.signup_succeed, username))
                                    }
                            } else showToast(R.string.signup_failed)
                            unblockSignup()
                        }
                }
            }

        }
    }

    private fun blockSignup() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSignup.isEnabled = false
    }

    private fun unblockSignup() {
        binding.progressBar.visibility = View.GONE
        binding.btnSignup.isEnabled = true
    }

    private fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(text: CharSequence) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}