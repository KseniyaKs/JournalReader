package com.example.articlestest.presentation.registration.user_data

object RegistrationUserDataViewState

sealed class RegistrationUserDataEvent {
    class CreateUserData(
        val name: String,
        val surname: String,
        val patronymic: String,
        val email: String,
    ) : RegistrationUserDataEvent()

}