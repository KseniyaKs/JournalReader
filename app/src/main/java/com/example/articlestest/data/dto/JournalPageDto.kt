package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class JournalPageDto(
    @SerializedName("id") val id: String,
    @SerializedName("comments") val commentsDto: List<CommentDto>,
    @SerializedName("like_count") val likeCount: Number,
    @SerializedName("is_liked") val isLike: Boolean,
    @SerializedName("journal") val journal: JournalDto,
    @SerializedName("number") val pageNumber: Number,
    @SerializedName("page_file") val pageFile: String
)

data class CommentDto(
    @SerializedName("id") val id: String,
    @SerializedName("user") val user: UserDto,
    @SerializedName("text") val commentText: String,
//    @SerializedName("page") val page: PageDto,
)

data class UserDto(
    @SerializedName("username") val username: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val surname: String
)

data class PageDto(
    @SerializedName("id") val id: String,
    @SerializedName("number") val pageNumber: Number,
    @SerializedName("page_file") val pageFile: String,
    @SerializedName("journal") val journalId: String
)

//PdfDocument.PageInfo
