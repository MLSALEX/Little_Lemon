package com.example.lttle_lemon_app.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lttle_lemon_app.MenuItemRoom
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    val totalAmount: StateFlow<Double> = _cartItems.map { items ->
        items.sumOf { it.menuItem.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> get() = _uiState

    fun addItemToCart(menuItem: MenuItemRoom, quantity: Int) {
        _cartItems.update { currentItems ->
            val updatedCartItems = currentItems.toMutableList()
            val existingItem = updatedCartItems.find { it.menuItem.id == menuItem.id }

            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
                updatedCartItems[updatedCartItems.indexOf(existingItem)] = updatedItem
            } else {
                updatedCartItems.add(CartItem(menuItem, quantity))
            }

            updatedCartItems
        }

        triggerAnimation()
    }

    private fun triggerAnimation() {
        _uiState.update { it.copy(isAnimating = true, showBadge = true) }

        viewModelScope.launch {
            delay(400)
            _uiState.update { it.copy(isAnimating = false, showBadge = false) }
        }
    }

    fun setQuantity(newQuantity: Int) {
        _uiState.update { it.copy(quantity = newQuantity) }
    }

    fun removeItemFromCart(menuItem: MenuItemRoom) {
        val updatedCartItems = _cartItems.value.toMutableList()
        val existingItem = updatedCartItems.find { it.menuItem.id == menuItem.id }

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                updatedCartItems[updatedCartItems.indexOf(existingItem)] = updatedItem
            } else {
                updatedCartItems.remove(existingItem)
            }
        }
        _cartItems.value = updatedCartItems
    }
}

data class CartItem(
    val menuItem: MenuItemRoom,
    val quantity: Int = 1,
)
data class CartUiState(
    val quantity: Int = 1,
    val isAnimating: Boolean = false,
    val showBadge: Boolean = false
)
