package com.example.articlestest.presentation.navigation


sealed class NavItem(var title: String, var screen_route: String) {
    object ArticleDetails : NavItem("ArticleDetails", "article_details")
}
