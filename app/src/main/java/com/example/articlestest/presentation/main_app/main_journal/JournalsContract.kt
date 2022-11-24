package com.example.articlestest.presentation.main_app.main_journal

import com.example.articlestest.data.model.JournalsData

data class JournalsViewState(
    val journalsData: JournalsData
)

sealed class JournalsEvent {
    object GetJournals : JournalsEvent()
    data class GetJournalDetails(
        val id: String
    ) : JournalsEvent()
}