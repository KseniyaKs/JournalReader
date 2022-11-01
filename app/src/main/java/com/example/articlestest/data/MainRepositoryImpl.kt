package com.example.articlestest.data

import com.example.articlestest.data.mapper.MapperFromJournalListDtoToModel
import com.example.articlestest.data.mapper.MapperFromJournalPageDtoToModel
import com.example.articlestest.data.model.Journal
import com.example.articlestest.data.model.JournalPage
import com.example.articlestest.data.model.JournalsData
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.ResponseMapper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: Api,
    private val mapper: ResponseMapper,
    private val mapperFromJournalListDtoToModel: MapperFromJournalListDtoToModel,
    private val mapperFromJournalPageDtoToModel: MapperFromJournalPageDtoToModel
) : MainRepository {

    override suspend fun getJournals(): JournalsData {
        val response = mapper.map(api.getJournals())
        return mapperFromJournalListDtoToModel.mapList(response)
    }

    override suspend fun getJournalDetails(id: String): Journal {
        val response = mapper.map(api.getJournalDetails(id))
        return mapperFromJournalListDtoToModel.map(response)
    }

    override suspend fun getPage(id: String): JournalPage {
        val response = mapper.map(api.getJournalPage(id))
        return mapperFromJournalPageDtoToModel.map(response)
    }
}