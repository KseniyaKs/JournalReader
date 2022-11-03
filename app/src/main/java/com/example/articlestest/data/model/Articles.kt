package com.example.articlestest.data.model

data class Articles(
    val count: Number,
    val next: Number?,
    val previous: Number?,
    val results: List<Article>
)

data class Article(
    val id: String,
    val imageDto: Image,
    val title: String,
    val description: String
)