package com.example.lttle_lemon_app

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lttle_lemon_app.components.Header
import com.example.lttle_lemon_app.components.LogButton

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()
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
                UserDataText("first name")
                UserDataText("last name")
                UserDataText("e-mail")
            }
        }
        LogButton("Log out", {})
    }
}


@Composable
fun UserDataText(
    userData: String
) {
    val sharedPreferences = LocalContext.current.getSharedPreferences(
        "UserData", Context.MODE_PRIVATE
    )
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = userData,
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            "${sharedPreferences.getString(userData, "")}",
            style = MaterialTheme.typography.bodyLarge
        )
        Divider()
    }

}

@Preview
@Composable
fun ProfileScreenPrev() {
    ProfileScreen()
}