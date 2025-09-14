package com.example.lttle_lemon_app.core.data.cart

import com.example.lttle_lemon_app.MenuItemRoom
import com.example.lttle_lemon_app.core.domain.cart.CartRepository
import com.example.lttle_lemon_app.screens.cartScreen.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CartRepositoryImpl : CartRepository {

    private val cart = MutableStateFlow<List<CartItem>>(emptyList())

    override fun observeCart(): Flow<List<CartItem>> = cart

    override suspend fun add(menuItem: MenuItemRoom, qty: Int) {
        cart.update { current ->
            val list = current.toMutableList()
            val i = list.indexOfFirst { it.menuItem.id == menuItem.id }
            if (i >= 0) {
                list[i] = list[i].copy(quantity = list[i].quantity + qty)
            } else {
                list.add(CartItem(menuItem, qty))
            }
            list
        }
    }

    override suspend fun decrease(menuItem: MenuItemRoom) {
        cart.update { current ->
            val list = current.toMutableList()
            val i = list.indexOfFirst { it.menuItem.id == menuItem.id }
            if (i >= 0) {
                val q = list[i].quantity - 1
                if (q > 0) list[i] = list[i].copy(quantity = q) else list.removeAt(i)
            }
            list
        }
    }

    override suspend fun setQuantity(menuItem: MenuItemRoom, qty: Int) {
        require(qty > 0)
        cart.update { current ->
            val list = current.toMutableList()
            val i = list.indexOfFirst { it.menuItem.id == menuItem.id }
            if (i >= 0) list[i] = list[i].copy(quantity = qty) else list.add(CartItem(menuItem, qty))
            list
        }
    }

    override suspend fun clear() {
        cart.value = emptyList()
    }
}