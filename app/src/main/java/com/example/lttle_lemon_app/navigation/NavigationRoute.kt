package com.example.lttle_lemon_app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object Onboarding : NavigationRoute

    @Serializable
    data object Home : NavigationRoute

    @Serializable
    data object Profile : NavigationRoute

    @Serializable
    data object Cart : NavigationRoute

    @Serializable
    data class MenuItemDetails(val dishId: Int) : NavigationRoute
}