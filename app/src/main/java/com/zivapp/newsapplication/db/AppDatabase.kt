package com.zivapp.newsapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zivapp.newsapplication.db.dao.ArticleDao
import com.zivapp.newsapplication.models.ArticlesDto
import com.zivapp.newsapplication.utils.ConvertersForDatabase

@Database(
    entities = [ArticlesDto::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ConvertersForDatabase::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}