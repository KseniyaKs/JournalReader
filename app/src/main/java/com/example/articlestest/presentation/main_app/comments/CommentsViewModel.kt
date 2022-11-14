package com.example.articlestest.presentation.main_app.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.articlestest.data.model.Article
import com.example.articlestest.data.model.Comment
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BaseViewState<CommentsViewState>, CommentsEvent>() {

    private var addCommentState = mutableListOf<Comment>()

    init {
        val article = savedStateHandle.get<Article>("articleCommentsArg")
        addCommentState = article?.comments?.toMutableList() ?: mutableListOf()
        setState(BaseViewState.Data(CommentsViewState(addCommentState, true)))
    }

    private fun sendComment(id: String, text: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val comment = repository.addArticleComment(id, text)
            addCommentState.add(comment)
            setState(BaseViewState.Data(CommentsViewState(addCommentState, true)))
        }
    }

    override fun onTriggerEvent(eventType: CommentsEvent) {
        when (eventType) {
            is CommentsEvent.Send -> sendComment(id = eventType.id, text = eventType.text)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }
}