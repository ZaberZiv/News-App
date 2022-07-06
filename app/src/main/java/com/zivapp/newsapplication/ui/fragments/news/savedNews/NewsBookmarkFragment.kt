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
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentNewsBookmarkBinding
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.SavedNewsAdapter
import com.zivapp.newsapplication.utils.BundleKeys.Companion.ARTICLE
import com.zivapp.newsapplication.utils.ViewBindingHolder
import com.zivapp.newsapplication.utils.ViewBindingHolderImpl
import com.zivapp.newsapplication.utils.setupToolbar
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsBookmarkFragment : Fragment(),
    ViewBindingHolder<FragmentNewsBookmarkBinding> by ViewBindingHolderImpl() {

    private val viewModel by viewModel<NewsBookmarkViewModel>()

    private val newsAdapter by inject<SavedNewsAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentNewsBookmarkBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupUi()
        setupRecycler()
        setupCollectors()
        setupListeners()
    }

    private fun setupUi() = requireBinding {
        toolbar.setupToolbar(this@NewsBookmarkFragment)
    }

    private fun setupRecycler() = requireBinding().rvNews.apply {
        adapter = newsAdapter
    }

    private fun setupCollectors() = requireBinding {
        lifecycleScope.launchWhenStarted {
            viewModel.articles.collect { articles ->
                emptyLayout.isVisible = articles.isEmpty()
                newsAdapter.differ.submitList(articles)
            }
        }
    }

    private fun setupListeners() {
        newsAdapter.setOnItemClickListener { article ->
            findNavController().navigate(
                R.id.action_newsBookmarkFragment_to_newsDetailsFragment,
                bundleOf(ARTICLE to article)
            )
        }

        newsAdapter.setOnBookmarkClickListener {
            viewModel.pickedArticle(it)
        }
    }
}