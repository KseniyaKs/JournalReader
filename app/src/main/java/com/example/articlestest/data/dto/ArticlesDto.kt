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
    @SerializedName("description") val description: String
)