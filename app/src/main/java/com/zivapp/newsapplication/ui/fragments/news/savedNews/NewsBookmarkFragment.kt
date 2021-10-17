package com.zivapp.newsapplication.ui.fragments.news.savedNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentNewsBookmarkBinding
import com.zivapp.newsapplication.ui.activity.MainActivity
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.SavedNewsAdapter
import com.zivapp.newsapplication.ui.fragments.news.NewsViewModel
import com.zivapp.newsapplication.utils.ViewBindingHolder
import com.zivapp.newsapplication.utils.ViewBindingHolderImpl
import com.zivapp.newsapplication.utils.setupToolbar
import kotlinx.coroutines.flow.collect

class NewsBookmarkFragment : Fragment(),
    ViewBindingHolder<FragmentNewsBookmarkBinding> by ViewBindingHolderImpl() {

    private val viewModel: NewsViewModel by lazy { (activity as MainActivity).newsViewModel }

    private val newsAdapter: SavedNewsAdapter by lazy { SavedNewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentNewsBookmarkBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupRecycler()
        setupUi()
    }

    private fun setupRecycler() = requireBinding().rvNews.apply {
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun setupUi() = requireBinding {
        requireBinding().toolbar.setupToolbar(this@NewsBookmarkFragment)

        lifecycleScope.launchWhenCreated {
            viewModel.getArticles().collect { articles ->
                Log.v("NewsBookmarkFragment", "articles -> $articles")

                emptyLayout.isVisible = if (articles.isNullOrEmpty()) {
                    true
                } else {
                    newsAdapter.differ.submitList(articles)
                    false
                }
            }
        }
        setupListeners()
    }

    private fun setupListeners() {
        newsAdapter.setOnItemClickListener { article ->
            findNavController().navigate(
                R.id.action_newsBookmarkFragment_to_newsDetailsFragment,
                Bundle().apply {
                    putSerializable("article", article)
                })
        }

        newsAdapter.setOnBookmarkClickListener {
            if (it.isPicked) {
                viewModel.deleteArticle(it.url)
            } else {
                viewModel.insertArticle(it)
            }
        }
    }
}