package com.example.articlestest.presentation.authorization.phone_check


object AuthorizationCheckViewStateEvent


sealed class AuthorizationCheckContractEvent {
    class PhoneCheck(
        val phone: String
    ) : AuthorizationCheckContractEvent()
}
