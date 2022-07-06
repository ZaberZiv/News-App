package com.zivapp.newsapplication.ui.fragments.news.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.FragmentTabNewsBinding
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.NewsAdapter
import com.zivapp.newsapplication.ui.paging.pagingAdapter.LoaderStateAdapter
import com.zivapp.newsapplication.utils.BundleKeys.Companion.ARTICLE
import com.zivapp.newsapplication.utils.ViewBindingHolder
import com.zivapp.newsapplication.utils.ViewBindingHolderImpl
import com.zivapp.newsapplication.utils.setupSwipeRefresh
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabNewsFragment(private val _filter: Int) : Fragment(),
    ViewBindingHolder<FragmentTabNewsBinding> by ViewBindingHolderImpl() {

    private val viewModel by viewModel<NewsViewModel>()

    private val newsAdapter: NewsAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(
        FragmentTabNewsBinding.inflate(layoutInflater, container, false),
        this
    ) {
        setupUi()
        collectNews()
    }

    private fun setupUi() = requireBinding {
        checkFilter()
        setupRecycler()
        setupListeners()
    }

    private fun checkFilter() {
        when (_filter) {
            1 -> disableSwipeToRefresh()
            2 -> {
                enableSwipeToRefresh()
                swipeToRefresh()
            }
        }
    }

    private fun setupRecycler() = requireBinding().rvNews.apply {
        adapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter { newsAdapter.retry() },
            footer = LoaderStateAdapter { newsAdapter.retry() },
        )
    }

    private fun setupListeners() = requireBinding {
        newsAdapter.setOnNewsClickListener { article ->
            findNavController().navigate(
                R.id.action_newsViewPagerFragment_to_newsDetailsFragment,
                Bundle().apply {
                    putSerializable(ARTICLE, article)
                })
        }

        newsAdapter.addLoadStateListener { loadState ->
            rvNews.isVisible = loadState.source.refresh is LoadState.NotLoading

            inputPagingLayout.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvTitle.isVisible = loadState.source.refresh is LoadState.Error
            }

            // empty layout
            if (loadState.source.refresh is LoadState.NotLoading
                && loadState.append.endOfPaginationReached
                && newsAdapter.itemCount < 1
            ) {
                emptyLayout.isVisible = true
                rvNews.isVisible = false
            } else {
                emptyLayout.isVisible = false
            }
        }

        inputPagingLayout.btnRetry.setOnClickListener {
            newsAdapter.retry()
        }
    }

    private fun collectNews() {
        when (_filter) {
            1 -> getTopHeadlinesNews()
            2 -> getEverythingNews()
        }
    }

    private fun getTopHeadlinesNews() = lifecycleScope.launchWhenStarted {
        viewModel.getTopHeadlinesNews("Apple").collectLatest {
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun getEverythingNews() = lifecycleScope.launchWhenCreated {
        viewModel.getEverythingNews("Apple").collectLatest {
            hideSwipeToRefresh()
            newsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun swipeToRefresh() {
        requireBinding().swipeToRefresh.setupSwipeRefresh {
            getEverythingNews()
        }
    }

    private fun hideSwipeToRefresh() {
        requireBinding().swipeToRefresh.isRefreshing = false
    }

    private fun disableSwipeToRefresh() {
        requireBinding().swipeToRefresh.isEnabled = false
    }

    private fun enableSwipeToRefresh() {
        requireBinding().swipeToRefresh.isEnabled = true
    }
}