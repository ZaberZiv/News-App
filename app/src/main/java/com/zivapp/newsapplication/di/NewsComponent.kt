package com.zivapp.newsapplication.di

import com.zivapp.newsapplication.repository.NewsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsComponent : KoinComponent {
    val newsRepository by inject<NewsRepository>()
}