package com.example.articlestest.presentation.main_app.profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewModelScope
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferences: DataStore<Preferences>
) : BaseViewModel<BaseViewState<ProfileViewState>, ProfileEvent>() {

    private fun logout() {
        viewModelScope.launch {
            sharedPreferences.edit {
                it.clear()
            }
            onNavigationEvent(NavDestination.AuthorizationPhone)
        }
    }

    override fun onTriggerEvent(eventType: ProfileEvent) {
        when (eventType) {
            ProfileEvent.Logout -> logout()
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }
}