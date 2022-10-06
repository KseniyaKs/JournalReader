package com.example.articlestest.domain


interface ArticleRepository {
    suspend fun likeArticle(isLike: Boolean): Boolean
}