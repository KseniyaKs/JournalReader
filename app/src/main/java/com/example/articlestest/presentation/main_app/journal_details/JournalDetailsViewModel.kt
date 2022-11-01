package com.example.articlestest.presentation.main_app.journal_details

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JournalDetailsViewModel @Inject constructor(
    private val repository: MainRepository,
) : BaseViewModel<BaseViewState<JournalDetailsViewState>, JournalDetailsEvent>() {

    private fun getJournal(id: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val journal = repository.getJournalDetails(id)
            setState(BaseViewState.Data(JournalDetailsViewState(journal)))
        }
    }


    override fun onTriggerEvent(eventType: JournalDetailsEvent) {
        when (eventType) {
            is JournalDetailsEvent.Get -> getJournal(eventType.id)
            JournalDetailsEvent.Buy -> {
                onNavigationEvent(eventType = NavDestination.BuyJournal)
            }
            is JournalDetailsEvent.Read -> {
                onNavigationEvent(eventType = NavDestination.ReadJournal(eventType.firstPageId))
            }
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }
}