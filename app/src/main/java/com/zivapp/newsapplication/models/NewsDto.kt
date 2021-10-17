package com.zivapp.newsapplication.models

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticlesDto>,
    )
