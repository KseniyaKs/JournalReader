package com.example.articlestest.presentation.splash

data class SplashViewState(
    val isNotEmptyProfile: Boolean
)

sealed class SplashEvent {
    data class CreateNewToken(
        val token: String
    ) : SplashEvent()

    object IsNotEmptyProfile : SplashEvent()
}