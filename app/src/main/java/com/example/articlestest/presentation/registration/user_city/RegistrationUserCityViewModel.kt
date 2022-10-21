package com.example.articlestest.presentation.registration.user_city

import com.example.articlestest.domain.RegistrationRepository
import com.example.articlestest.huinya.base.BaseViewModel
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegistrationUserCityViewModel @Inject constructor(
    private val repository: RegistrationRepository,
) : BaseViewModel<BaseViewState<RegistrationUserCityViewState>, RegistrationUserCityEvent>() {
    override fun onTriggerEvent(eventType: RegistrationUserCityEvent) {
        TODO("Not yet implemented")
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        TODO("Not yet implemented")
    }
}