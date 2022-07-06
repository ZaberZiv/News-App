package com.zivapp.newsapplication.di

import com.zivapp.newsapplication.db.AppDatabase
import com.zivapp.newsapplication.dispatchers.DispatchersProvider
import com.zivapp.newsapplication.dispatchers.DispatchersProviderImpl
import com.zivapp.newsapplication.network.NetworkServiceApi
import com.zivapp.newsapplication.network.RestApi
import com.zivapp.newsapplication.repository.NewsRepository
import com.zivapp.newsapplication.repository.NewsRepositoryImpl
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.NewsAdapter
import com.zivapp.newsapplication.ui.adapters.recyclerAdapters.SavedNewsAdapter
import com.zivapp.newsapplication.ui.fragments.news.home.tabs.NewsViewModel
import com.zivapp.newsapplication.ui.fragments.news.details.NewsDetailsViewModel
import com.zivapp.newsapplication.ui.fragments.news.savedNews.NewsBookmarkViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {
    single { RestApi().buildApi(NetworkServiceApi::class.java) }
    single { AppDatabase.getDatabase(androidApplication()) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
}

val coroutineModule = module {
    single<DispatchersProvider> { DispatchersProviderImpl() }
}

val viewModelModule = module {
    viewModel {
        NewsViewModel(get(), get())
    }
    viewModel {
        NewsBookmarkViewModel(get(), get())
    }
    viewModel {
        NewsDetailsViewModel(get(), get())
    }
}

val adapterModule = module {
    single { SavedNewsAdapter() }
    single { NewsAdapter() }
}