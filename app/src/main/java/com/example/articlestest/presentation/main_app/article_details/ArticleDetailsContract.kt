package com.example.articlestest.presentation.main_app.article_details

import com.example.articlestest.data.model.Article

data class ArticleDetailsViewState(
    val article: Article
)

sealed class ArticleDetailsEvent {
    data class Get(
        val id: String
    ) : ArticleDetailsEvent()
}