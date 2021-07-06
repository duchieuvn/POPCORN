package com.popcorn.cakey.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityAchievementBinding
import com.popcorn.cakey.mainscreen.Fragment1Activity
import com.popcorn.cakey.mainscreen.SuggestionActivity

class AchieveFragment : Fragment() {
    private var _binding: ActivityAchievementBinding?= null
    private val binding get() = _binding!!
    companion object{
        fun newInstance(): AchieveFragment {
            return AchieveFragment()
        }
    }
    override fun onResume() {
        super.onResume()
        val titles = resources.getStringArray(R.array.ac_lists)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, titles)
        binding.autoText.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ActivityAchievementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
