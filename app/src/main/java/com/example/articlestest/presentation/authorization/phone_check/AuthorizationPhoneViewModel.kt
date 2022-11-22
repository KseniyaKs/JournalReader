package com.example.articlestest.presentation.authorization.phone_check

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.AuthorisationRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationPhoneViewModel @Inject constructor(
    val repository: AuthorisationRepository,
) : BaseViewModel<BaseViewState<AuthorizationCheckViewStateEvent>, AuthorizationCheckContractEvent>() {

    private fun authorizationCheck(phone: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val authCheck = repository.checkPhone(phone)
            if (authCheck.is_authorized) {
                onNavigationEvent(NavDestination.AuthorizationPassword(phone))
            } else onNavigationEvent(NavDestination.RegistrationConfirmationCode(phone))
            setState(BaseViewState.Data(AuthorizationCheckViewStateEvent))
        }
    }

    override fun onTriggerEvent(eventType: AuthorizationCheckContractEvent) {
        when (eventType) {
            is AuthorizationCheckContractEvent.PhoneCheck -> authorizationCheck(eventType.phone)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
    }
}