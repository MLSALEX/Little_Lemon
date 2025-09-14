package com.example.lttle_lemon_app.screens.profile

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val isLoggedOut: Boolean = false
)


class ProfileViewModel (
    private val sharedPreferences:SharedPreferences
) : ViewModel(){

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


    fun logOut() {
        sharedPreferences.edit { clear() }
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}