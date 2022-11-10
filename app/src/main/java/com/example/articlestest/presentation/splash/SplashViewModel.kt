package com.example.articlestest.presentation.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.PreferenceKeys
import com.example.articlestest.domain.repositories.AuthorisationRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthorisationRepository,
    sharedPreferences: DataStore<Preferences>
) : BaseViewModel<BaseViewState<SplashViewState>, SplashEvent>() {

    private var token: String? = null

    init {
        viewModelScope.launch(coroutineExceptionHandler) {
            sharedPreferences.data.collect {
                token = it[PreferenceKeys.REFRESH_TOKEN]
            }
        }
    }

    fun initEvent() {
        token?.let {
            onTriggerEvent(eventType = SplashEvent.CreateNewToken(it))
            onTriggerEvent(eventType = SplashEvent.IsNotEmptyProfile)
        } ?: onTriggerEvent(eventType = SplashEvent.NewUser)
    }

    private fun createNewToken(token: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.createNewToken(token)
        }
    }

    private fun isNotEmptyProfile() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val isNotEmptyProfile = repository.isNotEmptyProfile()

            if (isNotEmptyProfile) {
                onNavigationEvent(eventType = NavDestination.AppMain)
            } else onNavigationEvent(eventType = NavDestination.RegistrationUserData)
        }
    }

    private fun createNewUser() {
        onNavigationEvent(eventType = NavDestination.AuthorizationPhone)
    }

    override fun onTriggerEvent(eventType: SplashEvent) {
        when (eventType) {
            is SplashEvent.CreateNewToken -> createNewToken(eventType.token)
            is SplashEvent.IsNotEmptyProfile -> isNotEmptyProfile()
            is SplashEvent.NewUser -> createNewUser()
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
    }

}