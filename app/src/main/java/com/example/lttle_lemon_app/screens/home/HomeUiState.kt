package com.example.lttle_lemon_app.screens.home

import com.example.lttle_lemon_app.MenuItemRoom

data class HomeUiState(
    val searchPhrase: String = "",
    val selectedCategory: String = "",
    val menuItems: List<MenuItemRoom> = emptyList(),
    val allMenuItems: List<MenuItemRoom> = emptyList()
)