package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.JournalDto
import com.example.articlestest.data.dto.JournalsDto
import com.example.articlestest.data.model.Image
import com.example.articlestest.data.model.Journal
import com.example.articlestest.data.model.JournalsData
import com.example.articlestest.data.model.Pdf
import javax.inject.Inject

interface MapperFromJournalListDtoToModel {
    fun mapList(journalList: JournalsDto): JournalsData
    fun map(journalsDto: JournalDto): Journal
}

class MapperFromJournalListDtoToModelImpl @Inject constructor() : MapperFromJournalListDtoToModel {

    override fun mapList(journalList: JournalsDto): JournalsData {
        val list = mutableListOf<Journal>()

        journalList.results.forEach {
            list.add(
                map(it)
            )
        }

        return JournalsData(
            count = journalList.count,
            journals = list
        )
    }

    override fun map(journalsDto: JournalDto): Journal {
        return Journal(
            id = journalsDto.id,
            image = Image(
                id = journalsDto.image.id,
                file = journalsDto.image.file
            ),
            pages = journalsDto.pages.map { pdf ->
                Pdf(id = pdf.id, page = pdf.page)
            },
            title = journalsDto.title,
            description = journalsDto.description,
            number = journalsDto.number,
            month = journalsDto.month,
            journalFile = journalsDto.journalFile,
            dateIssue = journalsDto.dateIssue,
            price = journalsDto.price,
            isBought = journalsDto.isBought
        )
    }


}