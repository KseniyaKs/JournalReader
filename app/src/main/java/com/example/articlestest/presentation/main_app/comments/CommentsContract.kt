package com.example.articlestest.presentation.main_app.comments

import com.example.articlestest.data.model.Comment

class CommentsViewState(
    val comments: List<Comment>,
    val scrollToBottom: Boolean = false
)

sealed class CommentsEvent {
    data class Send(
        val id: String,
        val text: String
    ) : CommentsEvent()
}