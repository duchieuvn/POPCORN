package com.popcorn.cakey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class Fragment2Activity: Fragment(R.layout.activity_fragment2) {
    companion object{
        fun newInstance(): Fragment2Activity{
           return Fragment2Activity()
        }
    }
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}