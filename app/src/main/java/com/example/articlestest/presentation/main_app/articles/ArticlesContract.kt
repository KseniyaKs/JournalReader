package com.example.articlestest.presentation.main_app.articles

import com.example.articlestest.data.model.Article
import com.example.articlestest.data.model.Articles

data class ArticlesViewState(
    val articles: Articles
)

sealed class ArticlesEvent {
    object GetArticles : ArticlesEvent()
    data class GetArticleDetails(
        val article: Article
    ) : ArticlesEvent()
}