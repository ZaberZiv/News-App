package com.zivapp.newsapplication.ui.fragments.news.savedNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zivapp.newsapplication.dispatchers.DispatchersProvider
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsBookmarkViewModel(
    private val repo: NewsRepository,
    private val dispatcher: DispatchersProvider
): ViewModel() {

    init {
        getArticles()
    }

    val articles = MutableStateFlow<List<ArticlesDto>>(listOf())
    private fun getArticles() = viewModelScope.launch(dispatcher.io) {
        repo.getArticles().collect {
            articles.value = it
        }
    }

    fun pickedArticle(article: ArticlesDto) {
        if (article.isPicked)
            deleteArticle(article.url)
        else
            insertArticle(article)
    }

    private fun deleteArticle(url: String) = viewModelScope.launch(dispatcher.io) {
        repo.deleteArticle(url)
    }

    private fun insertArticle(article: ArticlesDto) = viewModelScope.launch(dispatcher.io) {
        article.isPicked = true
        repo.insertArticle(article)
    }
}