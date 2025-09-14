package com.example.lttle_lemon_app.screens.onboarding

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val message: String? = null,
    val showMessage: Boolean = false,
    val isRegistered: Boolean = false
)

class OnboardingViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun onFirstNameChange(value: String) {
        _uiState.update { it.copy(firstName = value, message = null, showMessage = false) }
    }

    fun onLastNameChange(value: String) {
        _uiState.update { it.copy(lastName = value, message = null, showMessage = false) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, message = null, showMessage = false) }
    }


    fun onRegister() {
        val s = _uiState.value
        when {
            !isValidName(s.firstName) -> showError("Please enter a valid first name.")
            !isValidName(s.lastName)  -> showError("Please enter a valid last name.")
            !isValidEmail(s.email)    -> showError("Please enter a valid email address.")
            else -> {
                sharedPreferences.edit {
                    putString("firstName", s.firstName)
                    putString("lastName", s.lastName)
                    putString("email", s.email)
                    putBoolean("loggedIn", true)
                }
                _uiState.update {
                    it.copy(
                        message = "Registration successful!",
                        showMessage = true
                    )
                }
                viewModelScope.launch {
                    delay(2000)
                    _uiState.update { it.copy(isRegistered = true) }
                }
                viewModelScope.launch {
                    delay(3000)
                    _uiState.update { it.copy(showMessage = false) }
                }
            }
        }
    }

    private fun showError(msg: String) {
        _uiState.update { it.copy(message = msg, showMessage = true, isRegistered = false) }
        viewModelScope.launch {
            delay(3000)
            _uiState.update { it.copy(showMessage = false) }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidName(name: String): Boolean =
        name.isNotBlank() && name.length >= 2
}
