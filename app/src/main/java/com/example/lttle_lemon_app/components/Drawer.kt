package com.example.lttle_lemon_app.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.lttle_lemon_app.AppDatabase
import com.example.lttle_lemon_app.Navigation
import com.example.lttle_lemon_app.R
import kotlinx.coroutines.launch


@Composable
fun MyDrawer(navController: NavHostController, database: AppDatabase) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }
    val closeDrawer: () -> Unit = {
        scope.launch {
            drawerState.close()
        }
    }
    BackHandler(enabled = drawerState.isOpen) {
        closeDrawer()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent()
        }
    ) {
        Scaffold(
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Navigation(
                        navController = navController,
                        database = database,
                        openDrawer = openDrawer
                    )
                }
            }
        )
    }
}

@Composable
fun AppDrawerContent(modifier: Modifier = Modifier) {
    ModalDrawerSheet(modifier) {
        DrawerItem(
            label = stringResource(id = R.string.settings),
            icon = Icons.Default.Settings,
            onClick = { /* Handle Settings click */ }
        )
        DrawerItem(
            label = stringResource(id = R.string.address),
            icon = Icons.Default.Place,
            onClick = { /* Handle Address click */ }
        )
        DrawerItem(
            label = stringResource(id = R.string.phone),
            icon = Icons.Default.Phone,
            onClick = { /* Handle Phone click */ }
        )
    }
}

@Composable
fun DrawerItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        icon = { Icon(icon, contentDescription = null) },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}
