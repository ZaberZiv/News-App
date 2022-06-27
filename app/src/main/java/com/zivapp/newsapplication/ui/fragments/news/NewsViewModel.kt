package com.zivapp.newsapplication.ui.fragments.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repo: NewsRepository,
) : ViewModel() {

    fun getTopHeadlinesNews(query: String): Flow<PagingData<ArticlesDto>> {
        return repo.getTopHeadlinesNews(query)
            .cachedIn(viewModelScope)
    }

    fun getEverythingNews(query: String): Flow<PagingData<ArticlesDto>> {
        return repo.getEverythingNews(query)
            .cachedIn(viewModelScope)
    }

    fun insertArticle(articlesDto: ArticlesDto) = viewModelScope.launch(Dispatchers.Default) {
        articlesDto.isPicked = true
        repo.insertArticle(articlesDto)
    }

    fun getArticles(): Flow<List<ArticlesDto>> {
        return repo.getArticles()
    }

    fun getArticleByUrl(url: String): Flow<ArticlesDto?> {
        return repo.getArticleByUrl(url)
    }

    fun deleteArticle(url: String) = viewModelScope.launch(Dispatchers.Default) {
        repo.deleteArticle(url)
    }
}