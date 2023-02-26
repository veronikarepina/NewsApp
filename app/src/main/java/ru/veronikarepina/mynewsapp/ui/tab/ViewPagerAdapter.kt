package ru.veronikarepina.mynewsapp.ui.tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.veronikarepina.mynewsapp.ui.firstfragment.FirstFragment
import ru.veronikarepina.mynewsapp.ui.secondfragment.SecondFragment

class ViewPagerAdapter(tabFragment: TabFragment): FragmentStateAdapter(tabFragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FirstFragment()
            else -> SecondFragment()
        }
    }
}