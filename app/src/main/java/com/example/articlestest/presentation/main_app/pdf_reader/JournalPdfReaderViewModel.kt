package com.example.articlestest.presentation.main_app.pdf_reader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.articlestest.data.model.JournalPage
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JournalPdfReaderViewModel @Inject constructor(
    private val repository: MainRepository,
) : BaseViewModel<BaseViewState<PageLikeState>, JournalPageEvent>() {

    val journalPageState = MutableLiveData<JournalPage?>()

    private fun getPage(pageId: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            journalPageState.value = repository.getPage(pageId)
        }
    }

    private fun onLikeClick(page: JournalPage) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.changeJournalLikeStatus(page.id)
            onTriggerEvent(eventType = JournalPageEvent.GetPage(page.id))
        }
    }


    private fun onCommentClick() {
        onNavigationEvent(eventType = NavDestination.JournalPageComments(journalPageState.value!!))
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }

    override fun onTriggerEvent(eventType: JournalPageEvent) {
        when (eventType) {
            is JournalPageEvent.GetPage -> getPage(eventType.pageId)
            is JournalPageEvent.CommentClick -> onCommentClick()
            is JournalPageEvent.LikeClick -> onLikeClick(eventType.page)
        }
    }
}