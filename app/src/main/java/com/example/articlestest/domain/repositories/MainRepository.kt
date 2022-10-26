package com.example.articlestest.domain.repositories

import com.example.articlestest.data.model.JournalsData

interface MainRepository {
    suspend fun getJournals(): JournalsData
}