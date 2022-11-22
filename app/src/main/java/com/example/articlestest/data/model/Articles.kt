package com.example.articlestest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Articles(
    val count: Number,
    val next: Number?,
    val previous: Number?,
    val results: List<Article>
)

@Parcelize
data class Article(
    val id: String,
    val image: Image,
    val title: String,
    val description: String,
    val date: String,
    val comments: List<Comment>,
    val likeCount: Number,
    val isLiked: Boolean,
    val isCommented: Boolean,
    val articleFile: String
) : Parcelable
