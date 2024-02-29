package com.iamnaran.firefly.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iamnaran.firefly.ui.navigation.AppScreen
import com.iamnaran.firefly.ui.navigation.bottomappbar.BottomBar
import com.iamnaran.firefly.ui.navigation.multiplebackstack.RootMultipleBackStackNavHost
import com.iamnaran.firefly.ui.navigation.topappbar.AppTopBar
import com.iamnaran.firefly.ui.theme.FireflyComposeTheme
import com.iamnaran.firefly.utils.AppLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG: String = AppLog.tagFor(this.javaClass)
    private val mainViewModel: MainViewModel by viewModels()
    private var isAuthenticated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        isAuthenticated = mainViewModel.isUserAuthenticated()
        setContent {
            FireflyComposeTheme {
                MainScreenContent(isAuthenticated)
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    isAuthenticated: Boolean
) {
    val topAppbarTitle = remember { mutableStateOf("") }
    val topAppBarState = rememberTopAppBarState()
    val barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    // Control TopBar and BottomBar
    when (navBackStackEntry?.destination?.route) {
        AppScreen.Main.Home.route -> {
            bottomBarState.value = true
            topBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Home.title!!)

        }

        AppScreen.Main.Profile.route -> {
            bottomBarState.value = true
            topBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Profile.title!!)

        }

        AppScreen.Main.Notification.route -> {
            bottomBarState.value = true
            topBarState.value = true
            topAppbarTitle.value = stringResource(AppScreen.Main.Notification.title!!)

        }

        else -> {
            bottomBarState.value = false
            topBarState.value = false
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(barScrollBehavior.nestedScrollConnection),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            if (topBarState.value) {
                AppTopBar(topAppbarTitle.value, barScrollBehavior)
            }
        },
        bottomBar = {
            if (bottomBarState.value) {
                BottomBar(navController = navController)
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

            /*
            For normal back stack uncomment this and comment RootMultipleBackStackNavHost below nav host
            RootNavHost(
                isAuthenticated,
                navHostController = navController
            )*/

            // For multiple back stack nav host
            // Comment this if you need No Nested Nav Host
            RootMultipleBackStackNavHost(
                isAuthenticated,
                rootNavHostController = navController
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    FireflyComposeTheme {
        MainScreenContent(true)
    }
}


//https://gist.github.com/stevdza-san/5743a32dc1ec2d8eaf62c29d9bab1c43