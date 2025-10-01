package com.example.lttle_lemon_app.screens.home

import androidx.compose.runtime.Immutable
import com.example.lttle_lemon_app.MenuItemRoom
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeUiState(
    val search: String = "",
    val selected: Category = Category.All,
    val menuItems: ImmutableList<MenuItemRoom> = persistentListOf(),
    val allMenuItems: ImmutableList<MenuItemRoom> = persistentListOf()
)
