package com.example.lttle_lemon_app.screens.profile

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.Onboarding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = ""
)

class ProfileViewModel (private val sharedPreferences:SharedPreferences) : ViewModel(){

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val firstName = sharedPreferences.getString("firstName", "") ?: ""
        val lastName = sharedPreferences.getString("lastName", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""

        _uiState.value = ProfileUiState(firstName = firstName, lastName = lastName, email = email)
    }

    fun logOut(navController: NavHostController) {
        sharedPreferences.edit { clear() }
        navController.navigate(Onboarding.route){
            popUpTo(0) { inclusive = true }
        }
    }
}