package com.zivapp.newsapplication.di

import com.zivapp.newsapplication.db.AppDatabase
import com.zivapp.newsapplication.network.NetworkServiceApi
import com.zivapp.newsapplication.network.RestApi
import com.zivapp.newsapplication.repository.NewsRepository
import com.zivapp.newsapplication.repository.NewsRepositoryImpl
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.NewsAdapter
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.SavedNewsAdapter
import com.zivapp.newsapplication.ui.fragments.news.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule = module {
    single { SavedNewsAdapter() }
    single { NewsAdapter() }
    single { RestApi().buildApi(NetworkServiceApi::class.java) }
    single { AppDatabase.getDatabase(androidApplication()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    viewModel {
        NewsViewModel(get())
    }
}