package com.example.articlestest.presentation.main_app.article_details

import androidx.lifecycle.viewModelScope
import com.example.articlestest.data.model.Article
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val repository: MainRepository,
) : BaseViewModel<BaseViewState<ArticleDetailsViewState>, ArticleDetailsEvent>() {

    private fun getArticle(id: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val article = repository.getArticleDetails(id)
            setState(BaseViewState.Data(ArticleDetailsViewState(article)))
        }
    }

    private fun onLikeClick(article: Article) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val isLike = repository.changeArticleLikeStatus(article.id)
            val count = article.likeCount.toInt()
            val articleCopy = article.copy(
                isLiked = isLike,
                likeCount = if (isLike) count.plus(1) else count.minus(1),
            )
            setState(BaseViewState.Data(ArticleDetailsViewState(articleCopy)))
        }
    }

    private fun onCommentClick(article: Article) {
        onNavigationEvent(eventType = NavDestination.ArticleComments(article))
    }

    override fun onTriggerEvent(eventType: ArticleDetailsEvent) {
        when (eventType) {
            is ArticleDetailsEvent.Get -> getArticle(eventType.id)
            is ArticleDetailsEvent.CommentClick -> onCommentClick(eventType.article)
            is ArticleDetailsEvent.LikeClick -> onLikeClick(eventType.article)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }

}