package com.iamnaran.firefly.ui.navigation.topappbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.iamnaran.firefly.ui.theme.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildAppTopBar(
    toolbarTitle: String,
    navController: NavHostController,
    barScrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        scrollBehavior = barScrollBehavior,
        title = {
            Text(toolbarTitle, style = MaterialTheme.typography.titleMedium)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = AppIcons.BackArrow,
                    contentDescription = AppIcons.BackArrow.name
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onSecondary),
    )
}