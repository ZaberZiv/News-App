package com.zivapp.newsapplication.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SourceDto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
): Serializable
