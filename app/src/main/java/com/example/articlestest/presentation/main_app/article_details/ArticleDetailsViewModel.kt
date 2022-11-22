package com.example.articlestest.presentation.main_app.article_details

import androidx.lifecycle.MutableLiveData
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

    val articleState = MutableLiveData<Article?>()

    private fun getArticle(id: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            articleState.value = repository.getArticleDetails(id)
        }
    }

    private fun onLikeClick(article: Article) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.changeArticleLikeStatus(article.id)
            onTriggerEvent(eventType = ArticleDetailsEvent.Get(article.id))
        }
    }

    private fun onCommentClick() {
        onNavigationEvent(eventType = NavDestination.ArticleComments(articleState.value!!))
    }

    override fun onTriggerEvent(eventType: ArticleDetailsEvent) {
        when (eventType) {
            is ArticleDetailsEvent.Get -> getArticle(eventType.id)
            is ArticleDetailsEvent.CommentClick -> onCommentClick()
            is ArticleDetailsEvent.LikeClick -> onLikeClick(eventType.article)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }

}