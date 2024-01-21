package com.iamnaran.firefly.ui.navigation.bottomappbar

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.iamnaran.firefly.ui.navigation.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    navController: NavHostController
) {
    val navigationScreen = listOf(
        AppScreen.Main.Home, AppScreen.Main.Notification, AppScreen.Main.Profile
    )



    NavigationBar(
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }
        navigationScreen.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = selectedItemIndex == index,

                label = {
                    Text(text = stringResource(id = item.title!!))
                },
                icon = {
                    BadgedBox(badge = {
//                        if (item.badgeCount){
//                            Badge {
//                                Text(text = item.badgeCount.toString())
//                            }
//                        }else{
//
//                        }
                    }) {

                    }
                    Icon(imageVector = (if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon)!!,
                        contentDescription = stringResource(id = item.title!!)
                    )
                },

                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }

}