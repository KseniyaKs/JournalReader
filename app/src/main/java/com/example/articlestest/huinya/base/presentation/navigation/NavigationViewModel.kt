package com.example.articlestest.huinya.base.presentation.navigation

//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.saveable
//import dev.olshevski.navigation.reimagined.NavController
//import dev.olshevski.navigation.reimagined.navController
//import dev.olshevski.navigation.reimagined.navigate
//import dev.olshevski.navigation.reimagined.pop
//
//class NavigationViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
//
//    private val navController by savedStateHandle.saveable<NavController<AuthAndRegistrationViewModelsDestination>> {
//        navController(
//            startDestination = AuthAndRegistrationViewModelsDestination.AuthorizationPhone
//        )
//    }
//
////    private val navController2 by savedStateHandle.saveable<NavController<AppMainDestination>> {
////        navController(
////            startDestination = AppMainDestination.Magazine
////        )
////    }
//
//
//    // You may either make navController public or just its navBackstack. The latter is convenient
//    // when you don't want to expose navigation methods in the UI layer.
//    val navBackstack get() = navController.backstack
//    val backHandlerEnabled get() = navController.backstack.entries.size > 1
//
//    fun onBackPress() {
//        navController.pop()
//    }
//
//    fun onAuthPhoneContinueClick(isAuthorized: Boolean) {
////        navController.navigate(
////            if (isAuthorized) AuthAndRegistrationViewModelsDestination.AuthorizationPassword
////            else AuthAndRegistrationViewModelsDestination.RegistrationConfirmCode
////        )
//    }
//
//    fun onAuthPasswordContinueClick() {
//        navController.navigate(AuthAndRegistrationViewModelsDestination.AuthorizationConfirmCode)
//    }
//
//    fun onAuthConfirmCodeContinueClick() {
//        navController.navigate(AuthAndRegistrationViewModelsDestination.AuthorizationNewPassword)
//    }
//
//    fun onRegistConfirmCodeContinueClick () {
//        navController.navigate(AuthAndRegistrationViewModelsDestination.RegistrationPassword)
//    }
//
//    fun onRegistPasswordContinueClick () {
//        navController.navigate(AuthAndRegistrationViewModelsDestination.RegistrationUserData)
//    }
//
//    fun onRegistUserDataContinueClick() {
//        navController.navigate(AuthAndRegistrationViewModelsDestination.RegistrationUserCity)
//
//    }
//
//}