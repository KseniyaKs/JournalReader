package com.example.articlestest.presentation.navigation


sealed class NavItem(var title: String, var screen_route: String) {
    object ArticleDetails : NavItem("ArticleDetails", "article_details")
    object Splash : NavItem("Splash", "splash_screen")

    object AuthorizationNumberPhone :
        NavItem("AuthorizationNumberPhone", "authorization_check_screen")

    object AuthorizationPassword : NavItem("AuthorizationPassword", "authorization_password_screen")

    object RegistrationConfirmationCode :
        NavItem("RegistrationConfirmationCode", "registration_confirm_code_screen")

}
