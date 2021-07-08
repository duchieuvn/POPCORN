package com.popcorn.cakey.faqs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.popcorn.cakey.R


class FAQsActivity : AppCompatActivity() {
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    private val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val question_1 = ArrayList<String>()
            question_1.add("To gain experience: Post or rate blogs.")

            val question_2 = ArrayList<String>()
            question_2.add("To change your avatar: Go to Main Menu bar > Profile > Edit > Change avatar.")

            val question_3 = ArrayList<String>()
            question_3.add("To view special recipes: Go to Main Menu bar > Premium > Upgrade account.")

            val question_4 = ArrayList<String>()
            question_4.add("To change password: Go to Main Menu bar > Profile > Edit > Change password.")

            val question_5 = ArrayList<String>()
            question_5.add("To view baking courses: Go to Main Menu bar > Courses.")

            val question_6 = ArrayList<String>()
            question_6.add("Hehe")

            listData["How do I gain experience?"] = question_1
            listData["How do I change my profile's avatar?"] = question_2
            listData["How do I view special recipes?"] = question_3
            listData["How do I change my password?"] = question_4
            listData["How do I view baking courses?"] = question_5

            listData.toSortedMap(compareByDescending { it })
            return listData
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqs)
        expandableListView = findViewById(R.id.expandableListView)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.faqs)
        actionBar.setDisplayHomeAsUpEnabled(true)

        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
        }

    }
}