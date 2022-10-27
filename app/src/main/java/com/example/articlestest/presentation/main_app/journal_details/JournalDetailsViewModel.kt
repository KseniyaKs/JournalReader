package com.example.articlestest.presentation.main_app.journal_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.huinya.base.BaseViewModel
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JournalDetailsViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<BaseViewState<JournalDetailsViewState>, JournalDetailsEvent>() {

    //прописать ошибку елси пустой или null id
    init {
        val id = savedStateHandle.get<String>("ID")
        id?.let { onTriggerEvent(eventType = JournalDetailsEvent.GetJournal(id)) }
    }

    private fun getJournal(id: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            repository.getJournalDetails(id)
        }
    }


    override fun onTriggerEvent(eventType: JournalDetailsEvent) {
        when (eventType) {
            is JournalDetailsEvent.GetJournal -> getJournal(eventType.id)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
    }
}