package com.zivapp.newsapplication.utils

import android.app.Application
import com.zivapp.newsapplication.db.AppDatabase
import com.zivapp.newsapplication.network.NetworkServiceApi
import com.zivapp.newsapplication.network.RestApi
import com.zivapp.newsapplication.repository.RepositoryImpl

class RepositoryProvider {
    fun provideRepository(app: Application): RepositoryImpl {
        return RepositoryImpl(
            RestApi().buildApi(NetworkServiceApi::class.java),
            AppDatabase.getDatabase(app)
        )
    }
}