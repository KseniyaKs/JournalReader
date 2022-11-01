package com.example.articlestest.presentation.main_app.pdf_reader

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PdfReaderViewModel @Inject constructor(
    private val repository: MainRepository
) :
    BaseViewModel<BaseViewState<PdfReaderViewState>, PdfReaderEvent>() {

    private fun getPage(pageId: String) {
        viewModelScope.launch {
            repository.getPage(pageId)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }

    override fun onTriggerEvent(eventType: PdfReaderEvent) {
        when (eventType) {
            is PdfReaderEvent.GetPage -> getPage(eventType.pageId)
        }
    }
}