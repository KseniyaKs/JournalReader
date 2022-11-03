package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.JournalPageDto
import com.example.articlestest.data.model.Comment
import com.example.articlestest.data.model.JournalPage
import com.example.articlestest.data.model.Page
import com.example.articlestest.data.model.User
import javax.inject.Inject

interface MapperFromJournalPageDtoToModel {
    fun map(page: JournalPageDto): JournalPage
}

class MapperFromJournalPageDtoToModelImpl @Inject constructor(
    private val mapperFromJournalListDtoToModel: MapperFromJournalListDtoToModel
) : MapperFromJournalPageDtoToModel {

    override fun map(page: JournalPageDto): JournalPage {
        return JournalPage(
            id = page.id,
            comments = page.commentsDto.map { comment ->
                Comment(
                    id = comment.id,
                    user = User(
                        username = comment.user.username, firstName = comment.user.firstName,
                        surname = comment.user.surname
                    ),
                    commentText = comment.commentText,
                    page = Page(
                        id = comment.page.id,
                        pageNumber = comment.page.pageNumber,
                        pageFile = comment.page.pageFile,
                        journalId = comment.page.journalId
                    )
                )
            },
            likeCount = page.likeCount,
            journal = mapperFromJournalListDtoToModel.map(page.journal),
            pageNumber = page.pageNumber,
            pageFile = page.pageFile
        )
    }
}