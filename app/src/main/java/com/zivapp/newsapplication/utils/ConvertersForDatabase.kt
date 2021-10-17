package com.zivapp.newsapplication.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zivapp.newsapplication.models.SourceDto

class ConvertersForDatabase {
    @TypeConverter
    fun objToJson(value: SourceDto?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToObj(value: String) = Gson().fromJson(value, SourceDto::class.java)
}