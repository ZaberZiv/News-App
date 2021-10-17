package com.zivapp.newsapplication.ui.viewmodelProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zivapp.newsapplication.repository.RepositoryImpl
import com.zivapp.newsapplication.ui.fragments.news.NewsViewModel

@Suppress("UNCHECKED_CAST")
class NewsViewModelProvider(
    private val repo: RepositoryImpl
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(repo) as T
    }
}