package com.example.articlestest.presentation.main_app.journal

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.huinya.base.BaseViewModel
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class JournalsViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel<BaseViewState<JournalsViewState>, JournalsEvent>() {

    init {
        onTriggerEvent(eventType = JournalsEvent.GetJournals)
    }

    private fun getJournals() {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val journals = repository.getJournals()
            setState(BaseViewState.Data(JournalsViewState(journals)))
        }
    }

    override fun onTriggerEvent(eventType: JournalsEvent) {
        when (eventType) {
            JournalsEvent.GetJournals -> getJournals()
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
    }
}