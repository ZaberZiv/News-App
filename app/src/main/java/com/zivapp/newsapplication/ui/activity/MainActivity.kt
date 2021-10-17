package com.zivapp.newsapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.zivapp.newsapplication.R
import com.zivapp.newsapplication.databinding.ActivityMainBinding
import com.zivapp.newsapplication.repository.RepositoryImpl
import com.zivapp.newsapplication.ui.fragments.news.NewsViewModel
import com.zivapp.newsapplication.ui.viewmodelProvider.NewsViewModelProvider
import com.zivapp.newsapplication.utils.RepositoryProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    private val repository: RepositoryImpl by lazy { RepositoryProvider().provideRepository(application) }
    val newsViewModel by viewModels<NewsViewModel> {
        NewsViewModelProvider(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavHost()
    }

    private fun setupNavHost() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController
        val destination = R.id.newsViewPagerFragment

        navGraph.startDestination = destination
        navController.graph = navGraph
    }
}