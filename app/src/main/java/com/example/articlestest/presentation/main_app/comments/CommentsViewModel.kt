package com.example.articlestest.presentation.main_app.comments

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
class CommentsViewModel @Inject constructor(
    private val repository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<BaseViewState<CommentsViewState>, CommentsEvent>() {


    private fun sendComment(id: String, text: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.addArticleComment(id, text)
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