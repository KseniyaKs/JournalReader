package com.example.articlestest.presentation.registration.user_city

object RegistrationUserCityViewState

sealed class RegistrationUserCityEvent {
    class SignUp(
        val phone: String,
        val password: String
    ) : RegistrationUserCityEvent()
}