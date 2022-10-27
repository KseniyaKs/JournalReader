package com.example.articlestest.presentation.main_app.journal_details

object JournalDetailsViewState

sealed class JournalDetailsEvent {
    data class GetJournal(
        val id: String
    ) : JournalDetailsEvent()

}