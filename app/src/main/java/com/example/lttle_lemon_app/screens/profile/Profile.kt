package com.example.lttle_lemon_app.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.R
import com.example.lttle_lemon_app.components.LogButton
import com.example.lttle_lemon_app.components.TopAppBar
import com.example.lttle_lemon_app.viewModelFactory.AppViewModelFactory

@Composable
fun Profile(
    navController: NavHostController,
    openDrawer: () -> Unit,
    profileViewModel: ProfileViewModel
) {
    val uiState by profileViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            navController = navController,
            openDrawer = openDrawer
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = ""
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.personal_info),
                style = MaterialTheme.typography.bodyLarge
            )
            Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
                UserDataText("firstName", uiState.firstName)
                UserDataText("lastName", uiState.lastName)
                UserDataText("email", uiState.email)
            }
        }
        LogButton("Log out") {
            profileViewModel.logOut(navController)
        }
    }
}


@Composable
fun UserDataText(
    label: String,
    value: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
        HorizontalDivider()
    }
}