package com.example.articlestest.data.model

import com.example.articlestest.data.dto.JournalDto

data class JournalPage(
    val id: String,
    val comments: List<Comment>,
    val likeCount: Number,
    val journal: JournalDto,
    val pageNumber: Number,
    val pageFile: String
)

data class Comment(
    val id: String,
    val user: User,
    val commentText: String,
    val page: Page
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

