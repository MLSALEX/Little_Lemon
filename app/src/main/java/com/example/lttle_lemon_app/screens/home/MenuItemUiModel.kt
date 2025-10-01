package com.example.lttle_lemon_app.screens.home

import androidx.compose.runtime.Immutable

@Immutable
data class MenuItemUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val category: Category,
)

enum class Category { All, Starters, Mains, Desserts, Drinks }