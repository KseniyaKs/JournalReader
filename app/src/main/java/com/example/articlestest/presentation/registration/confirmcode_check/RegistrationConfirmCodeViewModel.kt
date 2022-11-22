package com.example.articlestest.presentation.registration.confirmcode_check

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.AuthorisationRepository
import com.example.articlestest.domain.repositories.RegistrationRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationConfirmCodeViewModel @Inject constructor(
    private val repository: RegistrationRepository,
    private val authorizationRepository: AuthorisationRepository,
) : BaseViewModel<BaseViewState<RegistrationConfirmCodeViewState>, RegistrationConfirmCodeEvent>() {

    private fun checkConfirmCode(phone: String, code: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch(coroutineExceptionHandler) {
            val confirmCodeCheck = repository.checkConfirmCode(phone = phone, code = code)
            if (confirmCodeCheck) {
                onNavigationEvent(NavDestination.RegistrationPassword(phone))
                delay(1000)
                setState((BaseViewState.Data(RegistrationConfirmCodeViewState)))
            } else setState(BaseViewState.Error(Throwable("Invalid confirmation code")))
        }
    }

    private fun createConfirmCode(phone: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch(coroutineExceptionHandler) {
            authorizationRepository.createConfirmCode(phone = phone)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
    }

    override fun onTriggerEvent(eventType: RegistrationConfirmCodeEvent) {
        when (eventType) {
            is RegistrationConfirmCodeEvent.CheckConfirmCode -> checkConfirmCode(
                phone = eventType.phone,
                code = eventType.code
            )
            is RegistrationConfirmCodeEvent.SendConfirmCode -> createConfirmCode(phone = eventType.phone)
        }

    }

}