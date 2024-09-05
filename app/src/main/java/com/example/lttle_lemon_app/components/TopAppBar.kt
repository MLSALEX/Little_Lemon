package com.example.lttle_lemon_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.CartScreen
import com.example.lttle_lemon_app.Home
import com.example.lttle_lemon_app.R
import com.example.lttle_lemon_app.ui.theme.LLColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(
    navController: NavHostController,
    openDrawer: () -> Unit,
    showMenuButton: Boolean = true,
    showLogo: Boolean = true,
    logoClickable: Boolean = true,
    showProfileImage: Boolean = false,
    showCart: Boolean = false,
    cartScale: Float = 1f,
    showBadge: Boolean = false,
    badgeOffset: Dp = 0.dp,
    onProfileClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Оптимальная высота для TopAppBar
            .padding(horizontal = 16.dp), // Уменьшение padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showMenuButton) {
            IconButton(onClick = openDrawer) {
                Image(
                    painter = painterResource(id = R.drawable.ic_hamburger_menu),
                    contentDescription = "Menu Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (showLogo) {
            Image(
                painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .clickable(enabled = logoClickable) {
                        navController.navigate(Home.route)
                    },
                contentScale = ContentScale.Inside
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (showProfileImage) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onProfileClick() }
            )
        }
        if (showCart) {
            Box(contentAlignment = Alignment.TopEnd) {
                IconButton(onClick = { navController.navigate(CartScreen.route) }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = "Cart",
                        modifier = Modifier
                            .size(24.dp)
                            .scale(cartScale)
                    )
                }
                if (showBadge) {
                    Box(
                        modifier = Modifier
                            .offset(x = (-10).dp, y = badgeOffset)
                            .size(20.dp)
                            .background(LLColor.green, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+1",
                            color = Color.White,
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }
            }
        }
    }
}