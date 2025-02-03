package com.iamnaran.firefly.ui.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iamnaran.firefly.R
import com.iamnaran.firefly.data.local.entities.UserEntity
import com.iamnaran.firefly.ui.main.interest.components.dialogs.SimpleAlertDialog
import com.iamnaran.firefly.ui.main.profile.component.LanguageItem
import com.iamnaran.firefly.ui.main.profile.component.ProfileCard
import com.iamnaran.firefly.ui.theme.appTypography
import com.iamnaran.firefly.ui.theme.dimens
import com.iamnaran.firefly.utils.extension.collectAsStateLifecycleAware
import com.iamnaran.firefly.utils.helper.AppLanguageHelper

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
) {
    val appLanguageHelper by lazy {
        AppLanguageHelper()
    }

    val selectedLanguageCode: String = appLanguageHelper.getLanguageCode(LocalContext.current)

    var selectedLanguage by remember { mutableStateOf(selectedLanguageCode) }

    val openAlertDialog = remember { mutableStateOf(false) }

    val user = profileViewModel.profileState.collectAsStateLifecycleAware()
    user.value.userEntityDetails?.let {
        ProfileContent(
            it, openAlertDialog, selectedLanguage,
        ) {
            openAlertDialog.value = true
        }
    }

    when {
        openAlertDialog.value -> {
            SimpleAlertDialog(
                dialogTitle = stringResource(id = R.string.prompt_logout_title),
                dialogSubTitle = stringResource(id = R.string.prompt_logout_description),
                onDismissRequest = {
                    openAlertDialog.value = false
                }) {
                openAlertDialog.value = false
                navigateToLogin()
                profileViewModel.doLogout()
            }
        }
    }
}

@Composable
fun ProfileContent(
    userEntity: UserEntity,
    openAlertDialog: MutableState<Boolean>,
    selectedLanguage: String,
    onLogoutClick: () -> Unit
) {

    Column(
        Modifier
            .padding(MaterialTheme.dimens.large)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileCard(userEntity)

        Button(
            onClick = {

                onLogoutClick()

            }, Modifier
                .padding(MaterialTheme.dimens.regular)
        ) {
            Text(text = "Logout")

        }


        Row(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.app_language),
                style = appTypography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(MaterialTheme.dimens.medium)
            )

            LanguageItem(selectedLanguage = selectedLanguage)
        }


    }
}

