package com.zivapp.newsapplication.ui.fragments.news.savedNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentNewsBookmarkBinding
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.SavedNewsAdapter
import com.zivapp.newsapplication.ui.fragments.news.NewsViewModel
import com.zivapp.newsapplication.utils.ViewBindingHolder
import com.zivapp.newsapplication.utils.ViewBindingHolderImpl
import com.zivapp.newsapplication.utils.setupToolbar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsBookmarkFragment : Fragment(),
    ViewBindingHolder<FragmentNewsBookmarkBinding> by ViewBindingHolderImpl() {

    private val viewModel by viewModel<NewsViewModel>()

    private val newsAdapter by inject<SavedNewsAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentNewsBookmarkBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupRecycler()
        setupUi()
        setupListeners()
    }

    private fun setupRecycler() = requireBinding().rvNews.apply {
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

    private fun setupUi() = requireBinding {
        requireBinding().toolbar.setupToolbar(this@NewsBookmarkFragment)

        lifecycleScope.launchWhenStarted {
            viewModel.getArticles().collect { articles ->
                emptyLayout.isVisible = articles.isEmpty()
                newsAdapter.differ.submitList(articles)
            }
        }
    }

    private fun setupListeners() {
        newsAdapter.setOnItemClickListener { article ->
            findNavController().navigate(
                R.id.action_newsBookmarkFragment_to_newsDetailsFragment,
                bundleOf("article" to article)
            )
        }

        newsAdapter.setOnBookmarkClickListener {
            if (it.isPicked)
                viewModel.deleteArticle(it.url)
            else
                viewModel.insertArticle(it)
        }
    }
}