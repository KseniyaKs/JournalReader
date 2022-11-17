package com.example.articlestest.presentation.main_app.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.articlestest.data.model.Article
import com.example.articlestest.data.model.Comment
import com.example.articlestest.data.model.JournalPage
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

    private var commentsList = mutableListOf<Comment>()
    private var commentTo: CommentTo = CommentTo.UNDEFINED
    private var id: String = ""

    init {
        when (val args = savedStateHandle.get<Any>("commentsArg")) {
            is Article -> {
                commentsList = args.comments.toMutableList()
                commentTo = CommentTo.ARTICLE
                id = args.id
            }
            is JournalPage -> {
                commentsList = args.comments.toMutableList()
                commentTo = CommentTo.JOURNAL_PAGE
                id = args.id
            }
        }
        setState(BaseViewState.Data(CommentsViewState(commentsList, commentsList.isNotEmpty())))
    }

    private fun sendComment(text: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val comment = if (commentTo == CommentTo.ARTICLE) {
                repository.addArticleComment(id, text)
            } else repository.addJournalComment(id, text)

            commentsList.add(comment)
            setState(BaseViewState.Data(CommentsViewState(commentsList, commentsList.isNotEmpty())))
        }
    }

    override fun onTriggerEvent(eventType: CommentsEvent) {
        when (eventType) {
            is CommentsEvent.Send -> sendComment(text = eventType.text)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }
}

enum class CommentTo {
    UNDEFINED, ARTICLE, JOURNAL_PAGE
}