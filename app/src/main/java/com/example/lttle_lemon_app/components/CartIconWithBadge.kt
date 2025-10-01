package com.example.lttle_lemon_app.components

import com.example.lttle_lemon_app.ui.theme.Cart

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CartIconWithBadge(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Cart,
    tint: Color = MaterialTheme.colorScheme.primary,
    iconSize: Dp = 30.dp,
    buttonSize: Dp = 48.dp,
    scale: Float = 1f,
    badgeVisible: Boolean,
    badgeAlignment: Alignment = Alignment.Center,
    badgeColor: Color = MaterialTheme.colorScheme.primary,
    badgeContentColor: Color = Color.White,
    badgeXOffset: Dp = (-4).dp,
    badgeYOffset: Dp = (-4).dp,
    badgeLabel: String
) {
    Box(
        modifier = modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(buttonSize)
                .semantics {
                    contentDescription = if (badgeVisible) {
                        "Cart, $badgeLabel items"
                    } else "Cart, empty"
                }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier
                    .size(iconSize)
            )
        }

        if (badgeVisible) {
            Badge(
                modifier = Modifier
                    .align(badgeAlignment)
                    .offset(x = badgeXOffset, y = badgeYOffset)
                    .scale(scale),
                containerColor = badgeColor,
                contentColor = badgeContentColor
            ) {
                Text(
                    text = badgeLabel,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }
    }
}