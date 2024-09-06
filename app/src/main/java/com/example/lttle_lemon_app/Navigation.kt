package com.example.lttle_lemon_app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lttle_lemon_app.screens.home.Home
import com.example.lttle_lemon_app.screens.cartScreen.CartScreen


import com.example.lttle_lemon_app.screens.cartScreen.CartViewModel
import com.example.lttle_lemon_app.screens.onboarding.Onboarding
import com.example.lttle_lemon_app.screens.onboarding.OnboardingViewModel
import com.example.lttle_lemon_app.screens.profile.Profile
import com.example.lttle_lemon_app.screens.profile.ProfileViewModel
import com.example.lttle_lemon_app.screens.MenuItemDetails
import com.example.lttle_lemon_app.viewModelFactory.AppViewModelFactory

@Composable
fun Navigation(
    navController: NavHostController,
    database: AppDatabase,
    openDrawer:() -> Unit
) {
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val viewModelFactory = AppViewModelFactory(sharedPreferences, database)
    val cartViewModel: CartViewModel = viewModel(factory = viewModelFactory)

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
            val onboardingViewModel: OnboardingViewModel = viewModel(
                factory = viewModelFactory
            )
            Onboarding(navController, onboardingViewModel, openDrawer)
        }
        composable(Home.route) {
            Home(navController, database, sharedPreferences, openDrawer)
        }
        composable(Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
            Profile(navController, openDrawer, profileViewModel,)
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

