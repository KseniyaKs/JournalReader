package com.example.articlestest.data.model


data class JournalPage(
    val id: String,
    val comments: List<Comment>,
    val likeCount: Number,
    val isLike: Boolean,
    val journal: Journal,
    val pageNumber: Number,
    val pageFile: String
)

data class Comment(
    val id: String,
    val user: User,
    val commentText: String,
//    val page: Page
)

data class User(
    val username: String,
    val firstName: String,
    val surname: String
)

data class Page(
    val id: String,
    val pageNumber: Number,
    val pageFile: String,
    val journalId: String
)

