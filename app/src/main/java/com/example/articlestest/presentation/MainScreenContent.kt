package com.example.articlestest.presentation.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.articlestest.presentation.navigation.NavItem


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreenContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            if (currentRoute(navController) != NavItem.ArticleDetails.screen_route) {
                BottomBarNavigation(navController = navController)
            }
        }
    ) {
            NavigationGraph(navController = navController)
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}