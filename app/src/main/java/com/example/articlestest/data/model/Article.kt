package com.example.articlestest.data.model

import java.io.Serializable

data class Article(
    val id: Int,
    val title: String,
    val author: String,
    val date: String,
    val imageId: Int = 0
): Serializable
