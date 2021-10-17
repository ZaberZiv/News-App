package com.zivapp.newsapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zivapp.newsapplication.models.ArticlesDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticlesDto)

    @Query("SELECT * FROM article_table")
    fun getArticles(): Flow<List<ArticlesDto>>

    @Query("SELECT * FROM article_table WHERE url = :url")
    fun getArticleByUrl(url: String): Flow<ArticlesDto?>

    @Query("DELETE FROM article_table WHERE url = :url")
    suspend fun deleteArticle(url: String)
}