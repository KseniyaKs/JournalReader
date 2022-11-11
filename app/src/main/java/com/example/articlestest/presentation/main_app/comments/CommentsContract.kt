package com.example.articlestest.presentation.main_app.comments

object CommentsViewState

sealed class CommentsEvent {
    data class Send(
        val id: String,
        val text: String
    ) : CommentsEvent()


}