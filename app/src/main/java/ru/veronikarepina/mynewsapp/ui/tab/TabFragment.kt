package ru.veronikarepina.mynewsapp.ui.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import ru.veronikarepina.mynewsapp.R
import ru.veronikarepina.mynewsapp.databinding.ActivityMainBinding
import ru.veronikarepina.mynewsapp.databinding.FragmentTabBinding

class TabFragment : Fragment() {
    private lateinit var binding: FragmentTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, pos ->
                when(pos){
                    0 -> tab.setText(R.string.news)
                    else -> tab.setText(R.string.favorite)
                }
        }.attach()
    }
}