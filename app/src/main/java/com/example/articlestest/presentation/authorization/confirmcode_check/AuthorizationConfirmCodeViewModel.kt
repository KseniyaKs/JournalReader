package com.example.articlestest.presentation.authorization.confirmcode_check

import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.AuthorisationRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorizationConfirmCodeViewModel @Inject constructor(
    private val repository: AuthorisationRepository,
) : BaseViewModel<BaseViewState<AuthorizationConfirmCodeViewState>, AuthorizationConfirmCodeEvent>() {

    private fun checkConfirmCode(phone: String, code: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch(coroutineExceptionHandler) {
            val confirmCodeCheck = repository.checkConfirmCode(phone = phone, code = code)
            setState(BaseViewState.Data(AuthorizationConfirmCodeViewState))
            if (confirmCodeCheck) {
                onNavigationEvent(NavDestination.AuthorizationNewPassword(phone = phone))
            } else setState(BaseViewState.Error(Throwable("Invalid confirmation code")))
        }
    }

    private fun createConfirmCode(phone: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.createConfirmCode(phone = phone)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
    }

    override fun onTriggerEvent(eventType: AuthorizationConfirmCodeEvent) {
        when (eventType) {
            is AuthorizationConfirmCodeEvent.CheckConfirmCode -> checkConfirmCode(
                phone = eventType.phone,
                code = eventType.code
            )
            is AuthorizationConfirmCodeEvent.SendConfirmCode -> createConfirmCode(phone = eventType.phone)
        }
    }
}