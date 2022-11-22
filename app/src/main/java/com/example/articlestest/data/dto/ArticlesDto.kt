package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class ArticlesDto(
    @SerializedName("count") val count: Number,
    @SerializedName("next") val next: Number?,
    @SerializedName("previous") val previous: Number?,
    @SerializedName("results") val results: List<ArticleDto>
)

data class ArticleDto(
    @SerializedName("id") val id: String,
    @SerializedName("image") val imageDto: ImageDto,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date_created") val date: String,
    @SerializedName("comments") val comments: List<CommentDto>,
    @SerializedName("like_count") val likeCount: Number,
    @SerializedName("is_liked") val isLiked: Boolean,
    @SerializedName("is_commented") val isCommented: Boolean,
    @SerializedName("article_file") val articleFile: String
)
