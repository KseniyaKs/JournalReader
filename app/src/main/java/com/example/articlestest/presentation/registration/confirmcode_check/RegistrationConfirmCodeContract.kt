package com.example.articlestest.presentation.registration.confirmcode_check

object RegistrationConfirmCodeViewState

sealed class RegistrationConfirmCodeEvent {
    class SendConfirmCode(
        val phone: String,
    ) : RegistrationConfirmCodeEvent()

    class CheckConfirmCode(
        val phone: String,
        val code: String
    ) : RegistrationConfirmCodeEvent()
}
