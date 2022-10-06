package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("data") val data: List<ArticleDto>
)

data class ArticleDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("date") val date: String,
    @SerializedName("imageId") val imageId: Int = 0
)
