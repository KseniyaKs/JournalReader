package com.example.articlestest.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.articlestest.presentation.navigation.NavDestination
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<STATE : BaseViewState<*>, EVENT> : ViewModel() {

    private val _uiState = MutableStateFlow<BaseViewState<*>>(BaseViewState.Empty)
    val uiState = _uiState.asStateFlow()

    val navigationState = MutableLiveData<NavDestination?>(null)

    val buttonState = MutableLiveData<ButtonState>()

    fun onSomeButtonClicked() {
        buttonState.value = ButtonState(false)
    }

    class ButtonState(
        val isEnabled: Boolean
    )

    protected val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    abstract fun onTriggerEvent(eventType: EVENT)

    open fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = null
    }

    protected fun setState(state: STATE) {
        _uiState.value = state
    }

    fun onBack() {
        navigationState.value = NavDestination.BackClick
    }

    protected suspend fun setEmitState(state: STATE) {
        _uiState.emit(state)
    }

    fun handleError(exception: Throwable) {
        _uiState.value = BaseViewState.Error(exception)
    }
}