package com.example.articlestest.data

import com.example.articlestest.data.mapper.MapperFromArticlesListToModel
import com.example.articlestest.data.mapper.MapperFromCommentDtoToModel
import com.example.articlestest.data.mapper.MapperFromJournalListDtoToModel
import com.example.articlestest.data.mapper.MapperFromJournalPageDtoToModel
import com.example.articlestest.data.model.*
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.ResponseMapper
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: Api,
    private val mapper: ResponseMapper,
    private val mapperFromJournalListDtoToModel: MapperFromJournalListDtoToModel,
    private val mapperFromJournalPageDtoToModel: MapperFromJournalPageDtoToModel,
    private val mapperFromArticlesListToModel: MapperFromArticlesListToModel,
    private val mapperFromCommentDtoToModel: MapperFromCommentDtoToModel
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

    override suspend fun getArticles(): Articles {
        val response = mapper.map(api.getArticles())
        return mapperFromArticlesListToModel.mapList(response)
    }

    override suspend fun getArticleDetails(id: String): Article {
        val response = mapper.map(api.getArticleDetails(id))
        return mapperFromArticlesListToModel.map(response)
    }

    override suspend fun addArticleComment(id: String, comment: String): Comment {
        val response = mapper.map(api.addArticlesComment(id, CommentBody(comment)))
        return mapperFromCommentDtoToModel.map(response)
    }

    override suspend fun changeLikeStatus(id: String): Boolean {
        val request = api.changeLikeStatus(id)
        var isLike = false

        if (request.isSuccessful) {
            when (request.code()) {
                201 -> isLike = true
                204 -> isLike = false
            }
        }
        return isLike
    }
}