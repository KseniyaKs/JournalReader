package com.example.articlestest.presentation.authorization.new_password

object AuthorizationNewPasswordViewState

sealed class AuthorizationNewPasswordEvent {
    class CreateNewPassword(
        val phone: String,
        val password: String,
        val newPassword: String
    ) : AuthorizationNewPasswordEvent()
}