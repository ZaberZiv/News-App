package com.zivapp.newsapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zivapp.newsapplication.utils.DateUtils
import java.io.Serializable

@Entity(tableName = "article_table")
data class ArticlesDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("source") val source: SourceDto? = null,
    @SerializedName("author") val author: String? = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("url") val url: String = "", //  "https://www.engadget.com/amc-theaters-accept-ethereum-litecoin-bitcoin-cash-132642183.html
    @SerializedName("urlToImage") val urlToImage: String = "", // https://s.yimg.com/os/creatr-uploaded-images/2021-09/4a01cb80-16eb-11ec-abfe-c7b840dd48ca"
    @SerializedName("publishedAt") val publishedAt: String = "", // "2021-09-16T13:26:42Z"
    @SerializedName("content") val content: String = "",
    var isPicked: Boolean = false,

    ): Serializable {
    fun getArticleForDateSeparator(date: String): ArticlesDto {
        return ArticlesDto(
            publishedAt = DateUtils.formatGMTToUTCDateStr(date) ?: "",
        )
    }
}
