package com.zivapp.newsapplication.ui.fragments.news.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentNewsViewPagerBinding
import com.zivapp.newsapplication.ui.adapters.tabAdapters.TabNewsAdapter
import com.zivapp.newsapplication.ui.fragments.news.home.tabs.TabNewsFragment
import com.zivapp.newsapplication.utils.ViewBindingHolder
import com.zivapp.newsapplication.utils.ViewBindingHolderImpl

class NewsViewPagerFragment : Fragment(),
    ViewBindingHolder<FragmentNewsViewPagerBinding> by ViewBindingHolderImpl() {

    data class Item(val title: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentNewsViewPagerBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupUi()
        setupTabLayout()
    }

    private fun setupUi()= requireBinding {
        toolbar.inflateMenu(R.menu.bookmark_menu)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.saved_articles -> {
                    findNavController().navigate(R.id.action_newsViewPagerFragment_to_newsBookmarkFragment)
                }
            }
            true
        }
    }

    private fun setupTabLayout() {
        val fragmentAdapter = TabNewsAdapter(this)
        val tabList = listOfItems()
        val fragmentList = listOfFragments()

        fragmentList.forEach { fragmentAdapter.addFragment(it) }

        binding?.apply {
            viewPager.adapter = fragmentAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
                tab.text = tabList[pos].title
            }.attach()
        }
    }

    private fun listOfItems(): List<Item> {
        return listOf(
            Item(getString(R.string.tab_top_headlines)),
            Item(getString(R.string.tab_everything)),
        )
    }

    private fun listOfFragments(): List<Fragment> {
        return listOf<Fragment>(
            TabNewsFragment(1),
            TabNewsFragment(2),
        )
    }
}