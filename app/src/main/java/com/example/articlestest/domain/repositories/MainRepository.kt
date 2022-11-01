package com.example.articlestest.domain.repositories

import com.example.articlestest.data.model.Journal
import com.example.articlestest.data.model.JournalPage
import com.example.articlestest.data.model.JournalsData

interface MainRepository {
    suspend fun getJournals(): JournalsData

    suspend fun getJournalDetails(id: String): Journal

    suspend fun getPage(id: String): JournalPage
}