package com.example.lttle_lemon_app

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.components.TopAppBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = viewModel(),
    openDrawer:() -> Unit
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalAmount = cartViewModel.getTotalAmount()

    var isAnimating by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween) {
        TopAppBar(
            navController = navController,
            openDrawer = openDrawer
        )

        Box {
            LazyColumn() {
                items(cartItems) { cartItem ->
                    CartItemRow(cartItem = cartItem, onIncrease = {
                        cartViewModel.addItemToCart(cartItem.menuItem, 1)
                        isAnimating = true
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
            onClick = { /* TODO: Implement checkout logic */ },
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