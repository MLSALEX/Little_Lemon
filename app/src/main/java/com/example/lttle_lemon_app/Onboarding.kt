package com.example.lttle_lemon_app

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lttle_lemon_app.components.Header
import com.example.lttle_lemon_app.components.LogButton
import com.example.lttle_lemon_app.ui.theme.Lttle_Lemon_appTheme


@Composable
fun Onboarding() {
    var firstName by rememberSaveable { mutableStateOf("") }

    var lastName by rememberSaveable { mutableStateOf("") }

    var eMail by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header()
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
            LogButton("Register", {})
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

@Preview
@Composable
fun OnboardinPrev() {
    Lttle_Lemon_appTheme {
        Onboarding()
    }
}
