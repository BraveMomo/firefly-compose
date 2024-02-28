package com.iamnaran.firefly.ui.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.iamnaran.firefly.ui.main.home.components.ProductLazyList
import com.iamnaran.firefly.utils.AppLog

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (String) -> Unit,
) {
    val homeState by viewModel.homeState.collectAsState()
    val loginStatus by viewModel.loginStatus.collectAsState()

    HomeContent(homeState) {
        onProductClick(it)
    }

}

@Composable
fun HomeContent(homeState: HomeState, onProductClick: (String) -> Unit) {
    AppLog.showLog("Home Screen Content")

    Column {
        ProductLazyList(homeState.allProductEntities) {
            onProductClick(it)
        }
    }

}

