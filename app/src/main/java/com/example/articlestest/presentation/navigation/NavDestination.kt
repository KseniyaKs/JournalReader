package com.example.articlestest.presentation.navigation

sealed class NavDestination {
    object AuthorizationPhone : NavDestination()

    data class AuthorizationPassword(val phone: String) : NavDestination()

    data class AuthorizationConfirmCode(val phone: String) : NavDestination()

    data class AuthorizationNewPassword(val phone: String) : NavDestination()

    object AppMain : NavDestination()

    class RegistrationConfirmationCode(val phone: String) : NavDestination()

    class RegistrationPassword(val phone: String) : NavDestination()

    object RegistrationUserData : NavDestination()

    class RegistrationUserCity(
        val name: String,
        val surname: String,
        val patronymic: String,
        val email: String,
    ) : NavDestination()

    data class JournalDetails(val id: String) : NavDestination()

    object BuyJournal : NavDestination()

    data class ReadJournal(val firstPageId: String) : NavDestination()

    data class ArticleDetails(val id: String) : NavDestination()

    object BackClick : NavDestination()
}
