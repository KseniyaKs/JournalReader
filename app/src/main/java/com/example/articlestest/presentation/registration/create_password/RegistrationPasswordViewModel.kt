package com.example.articlestest.presentation.registration.create_password

import androidx.lifecycle.SavedStateHandle
import com.example.articlestest.domain.RegistrationRepository
import com.example.articlestest.huinya.base.BaseViewModel
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegistrationPasswordViewModel @Inject constructor(
    private val repository: RegistrationRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<BaseViewState<RegistrationPasswordViewState>, RegistrationPasswordEvent>() {
    override fun onTriggerEvent(eventType: RegistrationPasswordEvent) {
        TODO("Not yet implemented")
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        TODO("Not yet implemented")
    }
}
