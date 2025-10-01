package com.example.lttle_lemon_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lttle_lemon_app.R


@Immutable
data class TopBarState(
    val nav: NavIcon = NavIcon.Menu,   // Menu | Back | None
    val showCart: Boolean = false,
    val cartCount: Int = 0,
    val cartScale: Float = 1f,
    val badgeOffsetY: Dp = 0.dp
) {
    val hasBadge: Boolean get() = showCart && cartCount > 0
    val badgeText: String get() = if (cartCount > 99) "99+" else cartCount.toString()
}

enum class NavIcon { None, Menu, Back }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: TopBarState,
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Keep callbacks stable
    val menuCb by rememberUpdatedState(onMenuClick)
    val backCb by rememberUpdatedState(onBackClick)
    val cartCb by rememberUpdatedState(onCartClick)

    CenterAlignedTopAppBar(
        navigationIcon = {
            when (state.nav) {
                NavIcon.Menu -> IconButton(
                    onClick = menuCb,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Open menu",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                NavIcon.Back -> IconButton(
                    onClick = backCb,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                NavIcon.None -> Unit
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.littlelemonimgtxt_nobg),
                    contentDescription = "Little Lemon",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .height(36.dp)
                )
            }
        },
        actions = {
            if (state.showCart) {
                CartIconWithBadge(
                    onClick = cartCb,
                    badgeVisible = state.hasBadge,
                    badgeLabel = state.badgeText,
                    scale = state.cartScale,
                    badgeXOffset = (1).dp,
                    badgeYOffset = (3).dp + state.badgeOffsetY,
                    tint = MaterialTheme.colorScheme.primary,
                    badgeColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

fun homeTopBar(cartCount: Int) = TopBarState(
    nav = NavIcon.Menu, showCart = true, cartCount = cartCount
)

val onboardingTopBar = TopBarState(
    nav = NavIcon.None, showCart = false
)

val profileTopBar = TopBarState(
    nav = NavIcon.Menu, showCart = false
)

fun detailsTopBar(cartCount: Int, scale: Float = 1f) = TopBarState(
    nav = NavIcon.Back, showCart = true, cartCount = cartCount, cartScale = scale
)