package com.example.articlestest.presentation.main_app.article_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BaseViewState<ArticleDetailsViewState>, ArticleDetailsEvent>() {

    //прописать ошибку елси пустой или null id
    init {
        val id = savedStateHandle.get<String>("articleId")
        id?.let { onTriggerEvent(eventType = ArticleDetailsEvent.Get(id)) }
    }

    private fun getArticle(id: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val article = repository.getArticleDetails(id)
            setState(BaseViewState.Data(ArticleDetailsViewState(article)))
        }
    }
//
//    fun likeArticle(isLike: Boolean) {
//        viewModelScope.launch(CoroutineExceptionHandler{  _, throwable ->
//            Log.d("exception", throwable.toString())
//        }) {
//            repository.likeArticle(isLike)
//        }
//    }

    override fun onTriggerEvent(eventType: ArticleDetailsEvent) {
        when (eventType) {
            is ArticleDetailsEvent.Get -> getArticle(eventType.id)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }

}