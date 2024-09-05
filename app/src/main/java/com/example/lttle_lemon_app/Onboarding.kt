package com.example.lttle_lemon_app

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.components.LogButton
import com.example.lttle_lemon_app.components.SnackBar
import com.example.lttle_lemon_app.components.TopAppBar
import kotlinx.coroutines.CoroutineScope


@Composable
fun Onboarding(
    navController: NavHostController,
    openDrawer:() -> Unit
) {
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    var firstName by rememberSaveable { mutableStateOf("") }

    var lastName by rememberSaveable { mutableStateOf("") }

    var eMail by rememberSaveable { mutableStateOf("") }
    var showMessage by rememberSaveable {
        mutableStateOf(false)
    }
    var message by rememberSaveable {
        mutableStateOf("")
    }

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
                Text(
                    text = "Let's get to know you",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Text(
                text = "Personal information:",
                style = MaterialTheme.typography.bodyLarge
            )

            Column(
                Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomOutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "First name",
                    placeholder = "Alex"
                )
                CustomOutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "Last name",
                    placeholder = "Smiley"
                )
                CustomOutlinedTextField(
                    value = eMail,
                    onValueChange = { eMail = it },
                    label = "e-mail",
                    placeholder = "alex.smiley@gmail.com"
                )
            }
            LogButton("Register") {
                when {
                    !isValidName(firstName) -> {
                        showMessage = true
                        message = "Please enter a valid first name."
                    }
                    !isValidName(lastName) -> {
                        showMessage = true
                        message = "Please enter a valid last name."
                    }
                    !isValidEmail(eMail) -> {
                        showMessage = true
                        message = "Please enter a valid email address."
                    }
                    else -> {
                        sharedPreferences.edit {
                            putString("firstName", firstName)
                            putString("lastName", lastName)
                            putString("email", eMail)
                            putBoolean("loggedIn", true)
                        }
                        navController.navigate(Home.route)
                        message = "Registration successful!"
                        showMessage = true
                    }
                }
            }
            if (showMessage) {
                SnackBar(message = message)
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