package com.example.articlestest.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.articlestest.presentation.screens.SplashScreen
import com.example.articlestest.presentation.screens.authorization.AuthorizationPasswordScreen
import com.example.articlestest.presentation.screens.authorization.RegistrationConfirmationCodeScreen
import com.example.articlestest.presentation.screens.authorization.authorization_check.AuthorizationNumberPhoneScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenContent(navHostController: NavHostController, finish: () -> Unit) {
    val navController = remember { navHostController }

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntryAsState?.destination?.route

    if (destination == "authorization_check_screen") {
        BackHandler { finish() }
    }

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") { SplashScreen(navController) }

        composable("authorization_check_screen") {
            AuthorizationNumberPhoneScreen()
        }

        composable("authorization_password_screen") { AuthorizationPasswordScreen(navController = navController) }

        composable("registration_confirm_code_screen") {
            RegistrationConfirmationCodeScreen(
                navController = navController
            )
        }
    }


//    MainAuthorizationScreen()
//    Scaffold(
//        bottomBar = {
//            if (currentRoute(navController) != NavItem.ArticleDetails.screen_route) {
//                BottomBarNavigation(navController = navController)
//            }
//        }
//    ) {
//            NavigationGraph(navController = navController)
//    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}