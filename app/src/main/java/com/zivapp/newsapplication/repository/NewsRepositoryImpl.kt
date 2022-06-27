package com.zivapp.newsapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.zivapp.newsapplication.db.AppDatabase
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.network.NetworkServiceApi
import com.zivapp.newsapplication.network.NetworkServiceApi.Companion.PAGE_SIZE
import com.zivapp.newsapplication.ui.paging.pagingSource.EverythingNewsPageSource
import com.zivapp.newsapplication.ui.paging.pagingSource.TopHeadlinesNewsPageSource
import com.zivapp.newsapplication.utils.ArticleSeparator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val service: NetworkServiceApi,
    dataBase: AppDatabase,
) : NewsRepository {

    private val articleDao = dataBase.articleDao()

    override fun getTopHeadlinesNews(
        query: String,
    ): Flow<PagingData<ArticlesDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TopHeadlinesNewsPageSource(service, query) }
        ).flow
            .map { pagingData ->
                pagingData.insertSeparators { before: ArticlesDto?, after: ArticlesDto? ->
                    ArticleSeparator.checksToInsertSeparator(before, after)
                }
            }
    }

    override fun getEverythingNews(
        query: String,
    ): Flow<PagingData<ArticlesDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { EverythingNewsPageSource(service, query) }
        ).flow
            .map { pagingData ->
                pagingData.insertSeparators { before: ArticlesDto?, after: ArticlesDto? ->
                    ArticleSeparator.checksToInsertSeparator(before, after)
                }
            }
    }

    override suspend fun insertArticle(articlesDto: ArticlesDto) {
        articleDao.insertArticle(articlesDto)
    }

    override fun getArticles(): Flow<List<ArticlesDto>> {
        return articleDao.getArticles()
    }

    override fun getArticleByUrl(url: String): Flow<ArticlesDto?> {
        return articleDao.getArticleByUrl(url)
    }

    override suspend fun deleteArticle(url: String) {
        articleDao.deleteArticle(url)
    }
}