package com.example.lttle_lemon_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lttle_lemon_app.R


@Composable
fun HeaderWithImage(content: @Composable () -> Unit = {}) {
    Header {
        content()
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "profile image",
            modifier = Modifier
                .width(50.dp)
                .clickable {}
        )
    }
}