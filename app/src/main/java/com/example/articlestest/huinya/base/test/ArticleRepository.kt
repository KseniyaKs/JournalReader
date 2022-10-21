package com.example.articlestest.huinya.base.test


interface ArticleRepository {
    suspend fun likeArticle(isLike: Boolean): Boolean
}