package com.popcorn.cakey.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R


class SuggestionFragment: Fragment(R.layout.activity_fragment1) {
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SuggestionActivity.ViewHolder>? = null
    companion object{
        fun newInstance():SuggestionFragment{
            return SuggestionFragment()
        }
    }
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View=inflater.inflate(R.layout.activity_fragment1,container,false)
        layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView=view.findViewById(R.id.suggestionView)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=SuggestionActivity()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.suggestionView.apply{
            layoutManager=LinearLayoutManager(activity)
            adapter=SuggestionActivity()
        }
    }
}
