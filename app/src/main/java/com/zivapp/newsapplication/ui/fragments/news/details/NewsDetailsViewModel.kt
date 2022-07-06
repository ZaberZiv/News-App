package com.zivapp.newsapplication.ui.fragments.news.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivapp.newsapplication.dispatchers.DispatchersProvider
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsDetailsViewModel(
    private val repo: NewsRepository,
    private val dispatcher: DispatchersProvider
): ViewModel() {

    fun getArticleByUrl(url: String): Flow<ArticlesDto?> {
        return repo.getArticleByUrl(url)
    }

    fun deleteArticle(url: String) = viewModelScope.launch(dispatcher.io) {
        repo.deleteArticle(url)
    }

    fun insertArticle(article: ArticlesDto) = viewModelScope.launch(dispatcher.io) {
        article.isPicked = true
        repo.insertArticle(article)
    }
}