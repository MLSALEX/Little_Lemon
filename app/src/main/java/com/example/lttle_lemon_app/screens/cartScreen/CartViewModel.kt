package com.example.lttle_lemon_app.screens.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lttle_lemon_app.MenuItemRoom
import com.example.lttle_lemon_app.core.domain.cart.CartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val repo: CartRepository
) : ViewModel() {
    val cartItems: StateFlow<List<CartItem>> =
        repo.observeCart()
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val totalAmount: StateFlow<Double> =
        cartItems
            .map { items -> items.sumOf { it.menuItem.price * it.quantity } }
            .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState

    fun addItemToCart(menuItem: MenuItemRoom, quantity: Int) {
        viewModelScope.launch {
            repo.add(menuItem, quantity)
            triggerAnimation()
        }
    }

    fun removeItemFromCart(menuItem: MenuItemRoom) {
        viewModelScope.launch { repo.decrease(menuItem) }
    }

    fun setQuantity(newQuantity: Int) {
        _uiState.update { it.copy(quantity = newQuantity) }
    }

    private fun triggerAnimation() {
        _uiState.update { it.copy(isAnimating = true, showBadge = true) }
        viewModelScope.launch {
            delay(400)
            _uiState.update { it.copy(isAnimating = false, showBadge = false) }
        }
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
