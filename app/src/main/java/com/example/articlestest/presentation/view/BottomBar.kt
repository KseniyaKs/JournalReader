package com.example.articlestest.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.articlestest.R
import com.example.articlestest.presentation.navigation.NavItem
import com.example.articlestest.ui.theme.Pink
import com.example.articlestest.ui.theme.Gray


sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {

    object Magazine : BottomNavItem("Magazine", R.drawable.ic_magazine, "magazine")
    object Articles : BottomNavItem("Articles", R.drawable.ic_articles, "articles")
    object Profile : BottomNavItem("Profile", R.drawable.ic_profile, "profile")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = BottomNavItem.Articles.screen_route,
        modifier = if (currentRoute(navController = navController) != NavItem.ArticleDetails.screen_route) {
            Modifier.padding(PaddingValues(bottom = 56.dp))
        } else Modifier
    ) {
        composable(BottomNavItem.Magazine.screen_route) {
            MagazineScreen()
        }
        composable(BottomNavItem.Articles.screen_route) {
            ArticlesScreen(navController)
        }
        composable(BottomNavItem.Profile.screen_route) {
            ProfileScreen()
        }
        composable(
            NavItem.ArticleDetails.screen_route + "/{article}/{image}",
            arguments = listOf(
                navArgument("article") { type = NavType.StringType },
                navArgument("image") { type = NavType.IntType }),
        ) {
            ArticleDetailsScreen(navController, it.arguments)
        }
    }
}

@Composable
fun BottomBarNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Magazine,
        BottomNavItem.Articles,
        BottomNavItem.Profile
    )
    val interactionSource = remember { MutableInteractionSource() }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {

        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white).copy(alpha = 0.94F),
            modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = if (currentRoute == item.screen_route) {
                                Pink
                            } else Gray
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 9.sp
                        )
                    },
                    selectedContentColor = Pink,
                    unselectedContentColor = Gray,
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.indication(
                        indication = null,
                        interactionSource = interactionSource
                    )
                )
            }
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}


