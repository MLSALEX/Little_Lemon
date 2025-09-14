package com.example.lttle_lemon_app.core.domain.cart

import com.example.lttle_lemon_app.MenuItemRoom
import com.example.lttle_lemon_app.screens.cartScreen.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<CartItem>>
    suspend fun add(menuItem: MenuItemRoom, qty: Int)
    suspend fun decrease(menuItem: MenuItemRoom)
    suspend fun setQuantity(menuItem: MenuItemRoom, qty: Int)
    suspend fun clear()
}