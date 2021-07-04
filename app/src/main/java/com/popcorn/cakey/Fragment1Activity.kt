package com.popcorn.cakey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Fragment1Activity: Fragment(R.layout.activity_fragment1) {
    companion object{
        fun newInstance():Fragment1Activity{
            return Fragment1Activity()
        }
    }
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}