package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.JournalPageDto
import com.example.articlestest.data.model.JournalPage
import javax.inject.Inject

interface MapperFromJournalPageDtoToModel {
    fun map(page: JournalPageDto): JournalPage
}

class MapperFromJournalPageDtoToModelImpl @Inject constructor(
    private val mapperFromJournalListDtoToModel: MapperFromJournalListDtoToModel,
    private val mapperFromCommentDtoToModel: MapperFromCommentDtoToModel
) : MapperFromJournalPageDtoToModel {

    override fun map(page: JournalPageDto): JournalPage {
        return JournalPage(
            id = page.id,
            comments = page.commentsDto.map { comment ->
                mapperFromCommentDtoToModel.map(comment)
            },
            isCommented = page.isCommented,
            isLike = page.isLiked,
            likeCount = page.likeCount,
            journal = mapperFromJournalListDtoToModel.map(page.journal),
            pageNumber = page.pageNumber,
            pageFile = page.pageFile
        )
    }
}