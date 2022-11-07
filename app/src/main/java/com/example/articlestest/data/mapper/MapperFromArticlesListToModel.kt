package com.example.articlestest.data.mapper

import com.example.articlestest.data.dto.ArticleDto
import com.example.articlestest.data.dto.ArticlesDto
import com.example.articlestest.data.model.Article
import com.example.articlestest.data.model.Articles
import com.example.articlestest.data.model.Image
import javax.inject.Inject

interface MapperFromArticlesListToModel {
    fun mapList(articlesDto: ArticlesDto): Articles
    fun map(articleDto: ArticleDto): Article
}

class MapperFromArticlesListToModelImpl @Inject constructor() : MapperFromArticlesListToModel {
    override fun mapList(articlesDto: ArticlesDto): Articles {
        return Articles(
            count = articlesDto.count,
            next = articlesDto.next,
            previous = articlesDto.previous,
            results = articlesDto.results.map {
                map(it)
            }
        )
    }

    override fun map(articleDto: ArticleDto): Article {
        return Article(
            id = articleDto.id,
            title = articleDto.title,
            description = articleDto.description,
            image = Image(
                id = articleDto.imageDto.id,
                file = articleDto.imageDto.file
            ),
            date = articleDto.date
        )
    }
}