package com.example.articlestest.presentation.main_app.journal

import com.example.articlestest.data.model.JournalsData

data class JournalsViewState(
    val journalsData: JournalsData
)

sealed class JournalsEvent {
    object GetJournals : JournalsEvent()
}