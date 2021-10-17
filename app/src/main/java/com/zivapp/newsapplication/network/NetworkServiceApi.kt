package com.zivapp.newsapplication.network

import com.zivapp.newsapplication.models.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServiceApi {
    companion object {
        const val PAGE_SIZE = 15
    }

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlinesNews(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): NewsDto

    @GET("/v2/everything")
    suspend fun getEverythingNews(
        @Query("q") query: String,
        @Query("page") page: Int,
    ): NewsDto
}