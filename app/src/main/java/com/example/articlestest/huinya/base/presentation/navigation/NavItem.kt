package com.example.articlestest.huinya.base.presentation.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//sealed class NavItem(var title: String, var screen_route: String) {
//    object Splash : NavItem("Splash", "splash_screen")
//
//    object AuthorizationContentScreen : NavItem("Authorization", "authorization_content_screen")

//    object AuthorizationNumberPhone :
//        NavItem("AuthorizationNumberPhone", "authorization_check_screen")
//
//    object AuthorizationPassword : NavItem("AuthorizationPassword", "authorization_password_screen")
//
//    object RegistrationConfirmationCode :
//        NavItem("RegistrationConfirmationCode", "registration_confirm_code_screen")
//
//    object Magazine : NavItem("Magazine", "magazine_screen")
//
//    object ArticleDetails : NavItem("ArticleDetails", "article_details")
//}

sealed class WelcomeDestination : Parcelable {
    @Parcelize
    object Splash : WelcomeDestination()

    @Parcelize
    object AuthorizationContent : WelcomeDestination()

//    @Parcelize
//    object AppMainContent : WelcomeDestination()
}

sealed class AuthAndRegistrationViewModelsDestination : Parcelable {

    @Parcelize
    data class AuthorizationPassword(val isAuth: Boolean) :
        AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object AuthorizationPhone : AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object AuthorizationConfirmCode : AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object AuthorizationNewPassword : AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object RegistrationConfirmCode : AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object RegistrationPassword : AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object RegistrationUserData : AuthAndRegistrationViewModelsDestination()

    @Parcelize
    object RegistrationUserCity : AuthAndRegistrationViewModelsDestination()

}

sealed class AppMainDestination : Parcelable {
    @Parcelize
    object Magazine : AppMainDestination()

}
