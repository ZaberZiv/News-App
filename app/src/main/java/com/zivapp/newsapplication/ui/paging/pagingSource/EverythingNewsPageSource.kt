package com.zivapp.newsapplication.ui.paging.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.network.NetworkServiceApi
import retrofit2.HttpException
import java.io.IOException

class EverythingNewsPageSource(
    private val serviceApi: NetworkServiceApi,
    private val query: String
) : PagingSource<Int, ArticlesDto>() {

    override fun getRefreshKey(state: PagingState<Int, ArticlesDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesDto> {
        val position = params.key ?: 1

        return try {
            val response = serviceApi.getEverythingNews(query, position)
            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (articles.isNullOrEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}