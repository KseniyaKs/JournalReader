package com.example.articlestest.presentation.main_app.pdf_reader

import com.example.articlestest.data.model.JournalPage

data class PdfReaderViewState(
    val page: JournalPage
)

sealed class PdfReaderEvent {
    data class GetPage(
        val pageId: String
    ) : PdfReaderEvent()

}