package com.example.articlestest.presentation.main_app.journal_pdf_reader

import com.example.articlestest.data.model.JournalPage

data class PageLikeState(
    val isLiked: Boolean,
    val likeCount: Number = 0
)

sealed class JournalPageEvent {
    data class GetPage(
        val pageId: String
    ) : JournalPageEvent()

    object CommentClick : JournalPageEvent()

    data class LikeClick(
        val page: JournalPage
    ) : JournalPageEvent()
}