package com.example.articlestest.presentation.authorization.new_password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.AuthorisationRepository
import com.example.articlestest.huinya.base.BaseViewModel
import com.example.articlestest.huinya.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationNewPasswordViewModel @Inject constructor(
    private val repository: AuthorisationRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<BaseViewState<AuthorizationNewPasswordViewState>, AuthorizationNewPasswordEvent>() {

    private fun createNewPassword(phone: String, password: String, newPassword: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.createNewPassword(
                phone = phone,
                password = password,
                newPassword = newPassword
            )
            onNavigationEvent(NavDestination.AppMain)
        }
    }

    override fun onTriggerEvent(eventType: AuthorizationNewPasswordEvent) {
        when (eventType) {
            is AuthorizationNewPasswordEvent.CreateNewPassword -> createNewPassword(
                phone = eventType.phone,
                password = eventType.password,
                newPassword = eventType.newPassword
            )
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
    }

}