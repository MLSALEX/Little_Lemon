package com.example.lttle_lemon_app.viewModelFactory

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lttle_lemon_app.AppDatabase
import com.example.lttle_lemon_app.screens.cartScreen.CartViewModel
import com.example.lttle_lemon_app.screens.home.HomeViewModel
import com.example.lttle_lemon_app.screens.onboarding.OnboardingViewModel
import com.example.lttle_lemon_app.screens.profile.ProfileViewModel

class AppViewModelFactory(
    private val sharedPreferences: SharedPreferences,
    private val database: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(sharedPreferences) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel() as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(sharedPreferences) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(database) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}