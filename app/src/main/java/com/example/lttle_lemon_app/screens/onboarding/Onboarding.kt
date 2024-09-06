package com.example.lttle_lemon_app.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.components.LogButton
import com.example.lttle_lemon_app.components.SnackBar
import com.example.lttle_lemon_app.components.TopAppBar


@Composable
fun Onboarding(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel = viewModel(),
    openDrawer: () -> Unit
) {
    val uiState by onboardingViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            navController = navController,
            logoClickable = false,
            showProfileImage = false,
            showMenuButton = false,
            openDrawer = openDrawer
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF495E57))
                    .padding(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Let's get to know you", style = MaterialTheme.typography.bodyMedium)
            }
            Text(text = "Personal information:", style = MaterialTheme.typography.bodyLarge)

            Column(Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                CustomOutlinedTextField(
                    value = uiState.firstName,
                    onValueChange = onboardingViewModel::onFirstNameChange,
                    label = "First name",
                    placeholder = "Alex"
                )
                CustomOutlinedTextField(
                    value = uiState.lastName,
                    onValueChange = onboardingViewModel::onLastNameChange,
                    label = "Last name",
                    placeholder = "Smiley"
                )
                CustomOutlinedTextField(
                    value = uiState.email,
                    onValueChange = onboardingViewModel::onEmailChange,
                    label = "e-mail",
                    placeholder = "alex.smiley@gmail.com"
                )
            }

            LogButton("Register") {
                onboardingViewModel.onRegister(navController)
            }

            if (uiState.showMessage) {
                SnackBar(message = uiState.message)
            }
        }
    }
}


@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
    )
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isValidName(name: String): Boolean {
    return name.isNotBlank() && name.length >= 2
}