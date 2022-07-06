package com.zivapp.newsapplication.ui.fragments.news.home.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zivapp.newsapplication.dispatchers.DispatchersProvider
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repo: NewsRepository,
    private val dispatcher: DispatchersProvider
) : ViewModel() {

    companion object {
        const val QUERY_APPLE = "Apple"
        const val QUERY_SAMSUNG = "Sumsung"
    }

    fun getTopHeadlinesNews(
        query: String = QUERY_APPLE
    ) = repo.getTopHeadlinesNews(query).cachedIn(viewModelScope)

    fun getEverythingNews(
        query: String = QUERY_SAMSUNG
    ) = repo.getEverythingNews(query).cachedIn(viewModelScope)
}