package com.example.articlestest.huinya.base.presentation.view

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
//
//    object Magazine : BottomNavItem("Magazine", R.drawable.ic_magazine, "magazine")
//    object Articles : BottomNavItem("Articles", R.drawable.ic_articles, "articles")
//    object Profile : BottomNavItem("Profile", R.drawable.ic_profile, "profile")
//}

//@Composable
//fun NavigationGraph(navController: NavHostController) {
//    NavHost(
//        navController,
//        startDestination = BottomNavItem.Articles.screen_route,
//        modifier = if (currentRoute(navController = navController) != NavItem.ArticleDetails.screen_route) {
//            Modifier.padding(PaddingValues(bottom = 56.dp))
//        } else Modifier
//    ) {
//        composable(BottomNavItem.Magazine.screen_route) {
//            MagazineScreen(navController = navController)
//        }
//        composable(BottomNavItem.Articles.screen_route) {
//            ArticlesScreen(navController)
//        }
//        composable(BottomNavItem.Profile.screen_route) {
//            ProfileScreen()
//        }
//        composable(
//            NavItem.ArticleDetails.screen_route + "/{article}/{image}",
//            arguments = listOf(
//                navArgument("article") { type = NavType.StringType },
//                navArgument("image") { type = NavType.IntType }),
//        ) {
//            ArticleDetailsScreen(navController, it.arguments)
//        }
//    }
//}

//@Composable
//fun BottomBarNavigation(navController: NavController) {
//    val items = listOf(
//        BottomNavItem.Magazine,
//        BottomNavItem.Articles,
//        BottomNavItem.Profile
//    )
//    val interactionSource = remember { MutableInteractionSource() }
//
//    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
//
//        BottomNavigation(
//            backgroundColor = colorResource(id = R.color.white).copy(alpha = 0.94F),
//            modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
//        ) {
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
//            items.forEach { item ->
//                BottomNavigationItem(
//                    icon = {
//                        Icon(
//                            painterResource(id = item.icon),
//                            contentDescription = item.title,
//                            tint = if (currentRoute == item.screen_route) {
//                                Pink
//                            } else Grey
//                        )
//                    },
//                    label = {
//                        Text(
//                            text = item.title,
//                            fontSize = 9.sp
//                        )
//                    },
//                    selectedContentColor = Pink,
//                    unselectedContentColor = Grey,
//                    alwaysShowLabel = true,
//                    selected = currentRoute == item.screen_route,
//                    onClick = {
//                        navController.navigate(item.screen_route) {
//
//                            navController.graph.startDestinationRoute?.let { screen_route ->
//                                popUpTo(screen_route) {
//                                    saveState = true
//                                }
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    },
//                    modifier = Modifier.indication(
//                        indication = null,
//                        interactionSource = interactionSource
//                    )
//                )
//            }
//        }
//    }
//}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}


