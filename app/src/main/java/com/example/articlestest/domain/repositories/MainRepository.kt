package com.example.articlestest.domain.repositories

import com.example.articlestest.data.model.*

interface MainRepository {
    suspend fun getJournals(): JournalsData

    suspend fun getJournalDetails(id: String): Journal

    suspend fun getPage(id: String): JournalPage

    suspend fun getArticles(): Articles

    suspend fun getArticleDetails(id: String): Article

    suspend fun addArticleComment(id: String, comment: String): Comment

    suspend fun changeLikeStatus(id: String): Boolean
}