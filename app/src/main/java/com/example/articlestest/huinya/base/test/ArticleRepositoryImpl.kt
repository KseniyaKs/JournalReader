package com.example.articlestest.huinya.base.test

import com.example.articlestest.data.Api
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: Api
) : ArticleRepository {
    override suspend fun likeArticle(isLike: Boolean): Boolean {
//        api.likeArticle(isLike)
        return !isLike
    }
}