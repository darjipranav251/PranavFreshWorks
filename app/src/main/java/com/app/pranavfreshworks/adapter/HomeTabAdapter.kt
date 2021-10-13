package com.app.pranavfreshworks.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.pranavfreshworks.fragment.FavouriteFragment
import com.app.pranavfreshworks.fragment.TrendingFragment

class HomeTabAdapter(var fragmentActivity: FragmentActivity, var totalTab: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalTab
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return TrendingFragment()
            1 -> return FavouriteFragment()
        }
        throw IllegalArgumentException()
    }
}