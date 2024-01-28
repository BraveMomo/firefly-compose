package com.iamnaran.firefly.ui.navigation

import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.iamnaran.firefly.ui.main.home.HomeScreen
import com.iamnaran.firefly.ui.main.home.productdetail.ProductScreen
import com.iamnaran.firefly.ui.main.notification.NotificationScreen
import com.iamnaran.firefly.ui.main.profile.ProfileScreen
import com.iamnaran.firefly.utils.AppLog

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    AppLog.showLog("Main NavGraph Setup")
    navigation(
        startDestination = AppScreen.Main.Home.route,
        route = AppScreen.Main.route
    ) {
        composable(
            route = AppScreen.Main.Home.route
        ) {
            HomeScreen(onProductClick = {
                navController.navigate(AppScreen.Main.Product.route)
            })
        }

        composable(route = AppScreen.Main.Notification.route) {
            NotificationScreen()
        }

        composable(route = AppScreen.Main.Profile.route) {
            ProfileScreen(navigateToLogin = {
                navController.navigate(AppScreen.Auth.route) {
                    popUpTo(AppScreen.Main.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(route = AppScreen.Main.Product.route, enterTransition = fadeIn(), exitTransition = expandIn()) {
            ProductScreen(){

            }
        }

    }

}