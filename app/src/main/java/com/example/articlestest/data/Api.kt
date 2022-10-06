package com.example.articlestest.data

import com.example.articlestest.data.dto.ArticleDto
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("qwerty")
    suspend fun likeArticle(@Query("isLike") isLike: Boolean): Response<ArticleDto>
}