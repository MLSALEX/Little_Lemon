package com.example.lttle_lemon_app.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun SnackBar(message: String) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Long)
    }
    SnackbarHost(hostState = snackbarHostState)
}