package com.zivapp.newsapplication.repository

import androidx.paging.PagingData
import com.zivapp.newsapplication.models.ArticlesDto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlinesNews(
        query: String,
    ): Flow<PagingData<ArticlesDto>>

    fun getEverythingNews(
        query: String,
    ): Flow<PagingData<ArticlesDto>>

    suspend fun insertArticle(articlesDto: ArticlesDto)

    fun getArticles(): Flow<List<ArticlesDto>>

    fun getArticleByUrl(url: String): Flow<ArticlesDto?>

    suspend fun deleteArticle(url: String)
}