package com.example.lttle_lemon_app.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.lttle_lemon_app.AppDatabase
import com.example.lttle_lemon_app.MenuItemRoom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeUiState(
    val searchPhrase: String = "",
    val selectedCategory: String = "",
    val menuItems: List<MenuItemRoom> = emptyList(),
    val allMenuItems: List<MenuItemRoom> = emptyList()
)
class HomeViewModel(private val database: AppDatabase) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    init {
        viewModelScope.launch {
            database.menuItemDao().getAll().asFlow().collect { menuItems ->
                _uiState.update { currentState ->
                    currentState.copy(
                        allMenuItems = menuItems,
                        menuItems = filterMenuItems(menuItems, currentState.searchPhrase, currentState.selectedCategory)
                    )
                }
            }
        }
    }

    fun onSearchPhraseChange(newPhrase: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchPhrase = newPhrase,
                menuItems = filterMenuItems(currentState.allMenuItems, newPhrase, currentState.selectedCategory)
            )
        }
    }

    fun onCategorySelected(newCategory: String) {
        val category = if (newCategory == "Remove Filter") "" else newCategory
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = category,
                menuItems = filterMenuItems(currentState.allMenuItems, currentState.searchPhrase, category)
            )
        }
    }

    private fun filterMenuItems(
        menuItems: List<MenuItemRoom>,
        searchPhrase: String,
        selectedCategory: String
    ): List<MenuItemRoom> {
        return menuItems.filter {
            (searchPhrase.isEmpty() || it.title.contains(searchPhrase, ignoreCase = true)) &&
                    (selectedCategory.isEmpty() || it.category.trim().equals(selectedCategory.trim(), ignoreCase = true))
        }
    }
}