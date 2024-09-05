package com.example.lttle_lemon_app

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.CoroutineScope

@Composable
fun Navigation(
    navController: NavHostController,
    database: AppDatabase,
    openDrawer:() -> Unit
) {
    val cartViewModel: CartViewModel = viewModel()

    val sharedPreferences =
        LocalContext.current.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    val isLoggedIn = sharedPreferences.getBoolean("loggedIn", false)

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) {
            Home.route
        } else {
            Onboarding.route
        }
    ) {
        composable(Onboarding.route) {
            Onboarding(navController, openDrawer)
        }
        composable(Home.route) {
            Home(navController, database, openDrawer)
        }
        composable(Profile.route) {
            Profile(navController, openDrawer)
        }
        composable(
            MenuItemDetails.route + "/{${MenuItemDetails.argDishId}}",
            arguments = listOf(navArgument(MenuItemDetails.argDishId) { type = NavType.IntType })
        ) {
            val id =
                requireNotNull(it.arguments?.getInt(MenuItemDetails.argDishId)) { "Dish id is null" }
            MenuItemDetails(navController, id, menuItemDao = database.menuItemDao(), cartViewModel, openDrawer)
        }
        composable(CartScreen.route) {
            CartScreen(navController, cartViewModel, openDrawer)
        }
    }
}

