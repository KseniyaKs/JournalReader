package com.example.articlestest.presentation.authorization.password_check


object AuthorizationPasswordViewState

sealed class AuthorizationPasswordEvent {
    class SignIn(
        val phone: String,
        val password: String,
    ) : AuthorizationPasswordEvent()
}


