package com.zivapp.newsapplication.utils

import com.zivapp.newsapplication.models.ArticlesDto

class ArticleSeparator {
    companion object {
        fun checksToInsertSeparator(before: ArticlesDto?, after: ArticlesDto?): ArticlesDto? {
            return when {
                before == null && after == null -> null // список пустой
                after == null -> null // FOOTER
                before == null -> separate(after.publishedAt) // HEADER
                before.publishedAt.shouldSeparate(after.publishedAt) -> separate(after.publishedAt)
                // Return null to avoid adding a separator between two items.
                else -> null
            }
        }

        private fun separate(afterPublishedAt: String): ArticlesDto {
            return ArticlesDto().getArticleForDateSeparator(afterPublishedAt)
        }
    }
}