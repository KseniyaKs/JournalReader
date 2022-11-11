package com.example.articlestest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class JournalsData(
    val count: Number,
    val journals: List<Journal>
)

@Parcelize
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
) : Parcelable

@Parcelize
data class Image(
    val id: String,
    val file: String
) : Parcelable

@Parcelize
data class Pdf(
    val id: String,
    val page: String
) : Parcelable
