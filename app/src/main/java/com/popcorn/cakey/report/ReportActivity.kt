package com.popcorn.cakey.report

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.parse.FunctionCallback
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.SaveCallback
import com.popcorn.cakey.R
import com.popcorn.cakey.blog.ReadBlogActivity
import com.popcorn.cakey.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.report)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val reportReason = arrayOf(
            "The recipe is incorrect.",
            "Inappropriate content.",
            "I\'m not interested in this recipe."
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, reportReason)

        binding.listReason.adapter = adapter
        binding.listReason.onItemClickListener =
            AdapterView.OnItemClickListener { _, view, position, _ -> // value of item that is clicked
                val itemValue = binding.listReason.getItemAtPosition(position) as String
                val reason = intent.putExtra("reason", itemValue)
                setResult(Activity.RESULT_OK, reason)
                finish()
            }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}