package com.example.lttle_lemon_app


import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lttle_lemon_app.screens.MenuItemDetails
import com.example.lttle_lemon_app.screens.cartScreen.CartScreen
import com.example.lttle_lemon_app.screens.home.Home
import com.example.lttle_lemon_app.screens.onboarding.Onboarding
import com.example.lttle_lemon_app.screens.profile.Profile
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController,
    openDrawer:() -> Unit
) {
    val prefs: SharedPreferences = koinInject()
    val isLoggedIn = prefs.getBoolean("loggedIn", false)

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
            Home(navController, openDrawer)
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
            MenuItemDetails(
                navController = navController,
                id = id,
                openDrawer = openDrawer
            )
        }
        composable(CartScreen.route) {
            CartScreen(navController, openDrawer)
        }
    }
}

