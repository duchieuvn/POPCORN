package com.popcorn.cakey.report

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.popcorn.cakey.R
import com.popcorn.cakey.blog.ReadBlogActivity
import com.popcorn.cakey.databinding.ActivityReportBinding
import com.popcorn.cakey.databinding.ActivityWriteBlogBinding

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.report)
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding = ActivityReportBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var reportReason = arrayOf("The recipe is incorrect.", "Inappropriate content.", "I\\'m not interested in this recipe.")

        val adapter = ArrayAdapter(this, R.layout.list_groups, reportReason)

        binding.listReason.adapter = adapter

        binding.listReason.onItemClickListener =
            AdapterView.OnItemClickListener { _, view, position, _ -> // value of item that is clicked
                val itemValue =  binding.listReason.getItemAtPosition(position) as String

                // Toast the values
                Toast.makeText(applicationContext,
                    "Position :$position\nItem Value : $itemValue", Toast.LENGTH_LONG)
                    .show()

                val i = Intent(this, ReadBlogActivity::class.java)
                i.putExtra("reason", itemValue)
                startActivity(i)
            }
    }
}