package com.example.articlestest.presentation.navigation

sealed class NavDestination {
    data class AuthorizationPassword(val phone: String) : NavDestination()

    data class AuthorizationConfirmCode(val phone: String) : NavDestination()

    data class AuthorizationNewPassword(val phone: String) : NavDestination()

    object AppMain : NavDestination()

    class RegistrationConfirmationCode(val phone: String) : NavDestination()

    object RegistrationPassword : NavDestination()

    object RegistrationUserData : NavDestination()

    object RegistrationUserCity : NavDestination()

    object BackClick : NavDestination()
}
