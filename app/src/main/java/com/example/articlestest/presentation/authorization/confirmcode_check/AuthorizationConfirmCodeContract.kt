package com.example.articlestest.presentation.authorization.confirmcode_check


object AuthorizationConfirmCodeViewState

sealed class AuthorizationConfirmCodeEvent {
    class SendConfirmCode(
        val phone: String,
    ) : AuthorizationConfirmCodeEvent()

    class CheckConfirmCode(
        val phone: String,
        val code: String
    ) : AuthorizationConfirmCodeEvent()
}
