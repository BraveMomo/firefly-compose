package com.iamnaran.firefly.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import com.iamnaran.firefly.ui.main.explore.ExploreScreen
import com.iamnaran.firefly.ui.main.home.HomeScreen
import com.iamnaran.firefly.ui.main.home.productdetail.ProductDetailScreen
import com.iamnaran.firefly.ui.main.notification.NotificationScreen
import com.iamnaran.firefly.ui.main.notification.recipe.recipedetail.RecipeDetailScreen
import com.iamnaran.firefly.ui.main.profile.ProfileScreen
import com.iamnaran.firefly.utils.AppLog

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    rootNavBackStackEntry: NavBackStackEntry?
) {

    navigation(
        startDestination = AppScreen.Main.Home.route,
        route = AppScreen.Main.route
    ) {
        composable(
            route = AppScreen.Main.Home.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable fadeOut(tween(700))
            },
        ) {
            HomeScreen(onProductClick = {
                val route = AppScreen.Main.ProductDetail.createRoute(productId = it)
                AppLog.showLog("Router---> $route")
                navController.navigate(route)
            })
        }


        composable(
            route = AppScreen.Main.Notification.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable fadeOut(tween(700))
            },
        ) {
            NotificationScreen() {
                val route = AppScreen.Main.RecipeDetail.createRoute(recipeId = it)
                navController.navigate(route)
            }
        }

        composable(
            route = AppScreen.Main.Profile.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable fadeOut(tween(700))
            },
        ) {
            ProfileScreen(navigateToLogin = {
                navController.navigate(AppScreen.Auth.route) {
                    popUpTo(AppScreen.Main.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = AppScreen.Main.Explore.route,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            },
            exitTransition = {
                return@composable fadeOut(tween(700))
            },
        ) {
            ExploreScreen(){

            }
        }

        dialog(
            route = AppScreen.Main.ProductDetail.route,
            dialogProperties = DialogProperties(usePlatformDefaultWidth = false)

        ) {

            // value also can be  retrieve directly from responsible view-model
            val productId = rootNavBackStackEntry?.arguments?.getString("productId")
            if (productId != null){
                ProductDetailScreen(productId = productId) {
                    navController.navigateUp()
                }
            }
        }

        dialog(
            route = AppScreen.Main.RecipeDetail.route,
            dialogProperties = DialogProperties(usePlatformDefaultWidth = false)

        ) {

            val recipeId = rootNavBackStackEntry?.arguments?.getString("recipeId")
            if (recipeId != null){
                RecipeDetailScreen(recipeId = recipeId) {
                    navController.navigateUp()
                }
            }


        }

    }

}