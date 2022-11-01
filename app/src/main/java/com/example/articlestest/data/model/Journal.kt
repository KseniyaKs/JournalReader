package com.example.articlestest.data.model


data class JournalsData(
    val count: Number,
    val journals: List<Journal>
)

data class Journal(
    val id: String,
    val image: Image,
    val pages: List<Pdf>,
    val title: String,
    val description: String,
    val number: Number,
    val month: String,
    val journalFile: String,
    val dateIssue: String,
    val price: String,
    val isBought: Boolean
)

data class Image(
    val id: String,
    val file: String
)

data class Pdf(
    val id: String,
    val page: String
)
