package com.example.articlestest.presentation.main_app.profile

object ProfileViewState

sealed class ProfileEvent {
    object Logout : ProfileEvent()
}