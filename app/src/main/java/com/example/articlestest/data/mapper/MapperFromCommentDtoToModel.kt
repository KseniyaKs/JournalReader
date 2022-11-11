package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.CommentDto
import com.example.articlestest.data.model.Comment
import com.example.articlestest.data.model.User
import javax.inject.Inject

interface MapperFromCommentDtoToModel {
    fun map(commentDto: CommentDto): Comment
}

class MapperFromCommentDtoToModelImpl @Inject constructor() : MapperFromCommentDtoToModel {
    override fun map(commentDto: CommentDto): Comment {
        return Comment(
            id = commentDto.id,
            user = User(
                username = commentDto.user.username,
                firstName = commentDto.user.firstName,
                surname = commentDto.user.surname
            ),
            commentText = commentDto.commentText
        )
    }
}