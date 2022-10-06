package com.example.articlestest.data

import com.example.articlestest.domain.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: Api
) : ArticleRepository {
    override suspend fun likeArticle(isLike: Boolean): Boolean {
        api.likeArticle(isLike)
        return !isLike
    }
}