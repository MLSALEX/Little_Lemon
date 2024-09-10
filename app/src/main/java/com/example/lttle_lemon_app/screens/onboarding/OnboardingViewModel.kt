package com.example.lttle_lemon_app.screens.onboarding

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.lttle_lemon_app.Home
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val message: String = "",
    val showMessage: Boolean = false
)

class OnboardingViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun onFirstNameChange(newFirstName: String) {
        _uiState.value = _uiState.value.copy(firstName = newFirstName)
    }

    fun onLastNameChange(newLastName: String) {
        _uiState.value = _uiState.value.copy(lastName = newLastName)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onRegister(navController: NavController) {
        when {
            !isValidName(_uiState.value.firstName) -> {
                _uiState.value = _uiState.value.copy(
                    message = "Please enter a valid first name.",
                    showMessage = true
                )
            }
            !isValidName(_uiState.value.lastName) -> {
                _uiState.value = _uiState.value.copy(
                    message = "Please enter a valid last name.",
                    showMessage = true
                )
            }
            !isValidEmail(_uiState.value.email) -> {
                _uiState.value = _uiState.value.copy(
                    message = "Please enter a valid email address.",
                    showMessage = true
                )
            }
            else -> {
                sharedPreferences.edit {
                    putString("firstName", _uiState.value.firstName)
                    putString("lastName", _uiState.value.lastName)
                    putString("email", _uiState.value.email)
                    putBoolean("loggedIn", true)
                }
                _uiState.value = _uiState.value.copy(
                    message = "Registration successful!",
                    showMessage = true
                )

                viewModelScope.launch {
                    delay(2000)
                    navController.navigate(Home.route)
                    resetShowMessage()
                }
            }
        }
    }
    private fun resetShowMessage() {
        viewModelScope.launch {
            delay(3000)
            _uiState.value = _uiState.value.copy(showMessage = false)
        }
    }
}
