package com.example.articlestest.data.dto

import com.google.gson.annotations.SerializedName

data class JournalsDto(
    @SerializedName("count") val count: Number,
    @SerializedName("results") val results: List<JournalDto>
)

data class JournalDto(
    @SerializedName("id") val id: String,
    @SerializedName("image") val image: ImageDto,
    @SerializedName("pages") val pages: List<PdfDto>,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("number") val number: Number,
    @SerializedName("month") val month: String,
    @SerializedName("journal_file") val journalFile: String,
    @SerializedName("date_of_issue") val dateIssue: String,
    @SerializedName("price") val price: String,
    @SerializedName("is_bought") val isBought: Boolean
)

data class ImageDto(
    @SerializedName("id") val id: String,
    @SerializedName("file") val file: String
)

data class PdfDto(
    @SerializedName("id") val id: String,
    @SerializedName("page_file") val page: String
)
