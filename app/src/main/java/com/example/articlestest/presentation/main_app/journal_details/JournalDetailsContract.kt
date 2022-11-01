package com.example.articlestest.presentation.main_app.journal_details

import com.example.articlestest.data.model.Journal

data class JournalDetailsViewState(
    val journal: Journal
)

sealed class JournalDetailsEvent {
    data class Get(
        val id: String
    ) : JournalDetailsEvent()

    object Buy : JournalDetailsEvent()

    data class Read(
        val firstPageId: String
    ) : JournalDetailsEvent()
}