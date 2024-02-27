package com.example.lttle_lemon_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.lttle_lemon_app.ui.theme.Lttle_Lemon_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lttle_Lemon_appTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}

