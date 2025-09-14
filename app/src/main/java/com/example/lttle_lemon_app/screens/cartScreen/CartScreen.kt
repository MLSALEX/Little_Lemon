package com.example.lttle_lemon_app.screens.cartScreen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lttle_lemon_app.R
import com.example.lttle_lemon_app.components.TopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    openDrawer: () -> Unit,
    onBack: () -> Unit,
    onCheckout: () -> Unit,
) {
    val activity = LocalContext.current as ComponentActivity
    val cartViewModel: CartViewModel = koinViewModel(viewModelStoreOwner = activity)
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalAmount by cartViewModel.totalAmount.collectAsState()
    val uiState by cartViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopAppBar(
            onMenuClick = openDrawer,
            showMenuButton = true,
            showLogo = true,
            logoClickable = true,
            showProfileImage = false,
            showCart = true,
            onCartClick = {},
            cartScale = if (uiState.isAnimating) 1.15f else 1f,
            showBadge = uiState.showBadge
        )

        Box {
            LazyColumn() {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onIncrease = {
                        cartViewModel.addItemToCart(cartItem.menuItem, 1)
                    }, onDecrease = {
                        cartViewModel.removeItemFromCart(cartItem.menuItem)
                    })
                    Divider()
                }
            }
        }



        Text(
            text = "Total: $${totalAmount}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        )

        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "checkout")
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem, onIncrease: () -> Unit, onDecrease: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = cartItem.menuItem.title,
            style = MaterialTheme.typography.bodyLarge
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = onDecrease
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = "Decrease quantity"
                )
            }

            Text(
                text = "${cartItem.quantity}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            IconButton(
                onClick = onIncrease
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Increase quantity"
                )
            }
        }

        Text(
            text = "$${cartItem.menuItem.price * cartItem.quantity}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End
        )
    }
}