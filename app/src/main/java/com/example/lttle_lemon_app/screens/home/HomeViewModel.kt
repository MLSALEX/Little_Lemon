package com.example.lttle_lemon_app.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.lttle_lemon_app.AppDatabase
import com.example.lttle_lemon_app.MenuItemRoom
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(private val database: AppDatabase) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            database.menuItemDao().getAll().asFlow().collect { rawMenuList ->
                _uiState.update { currentState ->
                    val newAllMenuItems = rawMenuList.toPersistentListReuse(currentState.allMenuItems)

                    val filtersDisabled = areFiltersOff(
                        searchText = currentState.search,
                        selectedCategory = currentState.selected
                    )

                    val newVisibleMenuItems =
                        if (filtersDisabled) {
                            newAllMenuItems
                        } else {
                            filterMenuItems(
                                allItems = newAllMenuItems,
                                searchText = currentState.search,
                                selectedCategory = currentState.selected
                            ).toPersistentListReuse(currentState.menuItems)
                        }

                    if (currentState.allMenuItems === newAllMenuItems &&
                        currentState.menuItems === newVisibleMenuItems
                    ) currentState
                    else currentState.copy(
                        allMenuItems = newAllMenuItems,
                        menuItems = newVisibleMenuItems
                    )
                }
            }
        }
    }

    fun onSearchChange(newSearch: String) {
        _uiState.update { currentState ->
            if (currentState.search == newSearch) return@update currentState

            val filtersDisabled = areFiltersOff(
                searchText = newSearch,
                selectedCategory = currentState.selected
            )

            val newVisibleMenuItems =
                if (filtersDisabled) {
                    currentState.allMenuItems
                } else {
                    filterMenuItems(
                        allItems = currentState.allMenuItems,
                        searchText = newSearch,
                        selectedCategory = currentState.selected
                    ).toPersistentListReuse(currentState.menuItems)
                }

            if (currentState.menuItems === newVisibleMenuItems) currentState
            else currentState.copy(search = newSearch, menuItems = newVisibleMenuItems)
        }
    }

    fun onCategoryChange(newCategory: Category) {
        _uiState.update { currentState ->
            if (currentState.selected == newCategory) return@update currentState

            val filtersDisabled = areFiltersOff(
                searchText = currentState.search,
                selectedCategory = newCategory
            )

            val newVisibleMenuItems =
                if (filtersDisabled) {
                    currentState.allMenuItems
                } else {
                    filterMenuItems(
                        allItems = currentState.allMenuItems,
                        searchText = currentState.search,
                        selectedCategory = newCategory
                    ).toPersistentListReuse(currentState.menuItems)
                }

            if (currentState.menuItems === newVisibleMenuItems) currentState
            else currentState.copy(selected = newCategory, menuItems = newVisibleMenuItems)
        }
    }

    private fun filterMenuItems(
        allItems: ImmutableList<MenuItemRoom>,
        searchText: String,
        selectedCategory: Category
    ): List<MenuItemRoom> {
        val trimmedSearchText = searchText.trim()

        if (areFiltersOff(trimmedSearchText, selectedCategory)) return allItems

        val selectedCategoryStorageValue: String? = when (selectedCategory) {
            Category.All -> null
            else -> selectedCategory.toStorageString()
        }

        return allItems.asSequence()
            .filter { item ->
                val matchesSearch =
                    trimmedSearchText.isEmpty() ||
                            item.title.contains(trimmedSearchText, ignoreCase = true)

                val matchesCategory =
                    selectedCategoryStorageValue?.let { storageValue ->
                        item.category.trim().equals(storageValue, ignoreCase = true)
                    } ?: true

                matchesSearch && matchesCategory
            }
            .toList()
    }

    private fun areFiltersOff(searchText: String, selectedCategory: Category): Boolean {
        return searchText.isBlank() && selectedCategory == Category.All
    }

    private fun Category.toStorageString(): String = when (this) {
        Category.Starters -> "Starters"
        Category.Mains -> "Mains"
        Category.Desserts -> "Desserts"
        Category.Drinks -> "Drinks"
        Category.All -> ""
    }
}

private fun <T> List<T>.toPersistentListReuse(previous: ImmutableList<T>): ImmutableList<T> {
    return if (previous.size == size && previous == this) previous else toPersistentList()
}