package com.example.articlestest.presentation.main_app.articles

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel<BaseViewState<ArticlesViewState>, ArticlesEvent>() {

    init {
        onTriggerEvent(eventType = ArticlesEvent.GetArticles)
    }

    private fun getArticles() {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val articles = repository.getArticles()
            setState(BaseViewState.Data(ArticlesViewState(articles)))
        }
    }

    override fun onTriggerEvent(eventType: ArticlesEvent) {
        when (eventType) {
            ArticlesEvent.GetArticles -> getArticles()
            is ArticlesEvent.GetArticleDetails -> onNavigationEvent(
                NavDestination.ArticleDetails(
                    eventType.article
                )
            )
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }
}