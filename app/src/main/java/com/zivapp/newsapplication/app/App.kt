package com.zivapp.newsapplication.app

import android.app.Application
import com.zivapp.newsapplication.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(serviceModule, coroutineModule, viewModelModule, adapterModule)
        }
    }
}