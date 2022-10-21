package com.example.articlestest.presentation.registration.create_password


object RegistrationPasswordViewState

sealed class RegistrationPasswordEvent {
    class SignUp(
        val phone: String,
        val password: String
    ) : RegistrationPasswordEvent()
}