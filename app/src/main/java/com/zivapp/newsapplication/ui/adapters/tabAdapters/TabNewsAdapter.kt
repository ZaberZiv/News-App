package com.zivapp.newsapplication.ui.adapters.tabAdapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabNewsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragments: ArrayList<Fragment> = arrayListOf()

    override fun getItemCount(): Int = fragments.size

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}