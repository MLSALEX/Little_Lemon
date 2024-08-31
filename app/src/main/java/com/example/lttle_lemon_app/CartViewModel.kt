package com.example.lttle_lemon_app

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    fun addItemToCart(menuItem: MenuItemRoom, quantity: Int) {
        val updatedCartItems = _cartItems.value.toMutableList()
        val existingItem = updatedCartItems.find { it.menuItem.id == menuItem.id }

        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
            updatedCartItems[updatedCartItems.indexOf(existingItem)] = updatedItem
        } else {
            updatedCartItems.add(CartItem(menuItem, quantity))
        }

        _cartItems.value = updatedCartItems
    }

    fun removeItemFromCart(menuItem: MenuItemRoom) {
        val updatedCartItems = _cartItems.value.toMutableList()
        val existingItem = updatedCartItems.find { it.menuItem.id == menuItem.id }

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                // Уменьшаем количество, если больше 1
                val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                updatedCartItems[updatedCartItems.indexOf(existingItem)] = updatedItem
            } else {
                // Удаляем элемент из списка, если количество 1 или меньше
                updatedCartItems.remove(existingItem)
            }
        }

        _cartItems.value = updatedCartItems
    }

    fun getTotalAmount(): Double {
        return _cartItems.value.sumOf { it.menuItem.price * it.quantity }
    }
}

data class CartItem(
    val menuItem: MenuItemRoom,
    val quantity: Int
)

