package com.example.lttle_lemon_app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.lttle_lemon_app.ui.theme.LLColor


@Composable
fun LogButton(
    buttonText: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = { onClick },
        colors = ButtonDefaults.buttonColors(containerColor = LLColor.yellow),
        shape = shapes.medium,
        modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        Text(
            text = buttonText,
            color = Color.Black
        )
    }
}
