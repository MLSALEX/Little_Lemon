package com.example.lttle_lemon_app.navigation


import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.lttle_lemon_app.screens.MenuItemDetails
import com.example.lttle_lemon_app.screens.cartScreen.CartScreen
import com.example.lttle_lemon_app.screens.home.HomeRoute
import com.example.lttle_lemon_app.screens.onboarding.Onboarding
import com.example.lttle_lemon_app.screens.profile.Profile
import org.koin.compose.koinInject

@Composable
fun Navigation(
    navController: NavHostController,
    openDrawer:() -> Unit,
    cartCount: Int
) {
    val prefs: SharedPreferences = koinInject()
    val isLoggedIn = prefs.getBoolean("loggedIn", false)


    val start: NavigationRoute = if (isLoggedIn) {
        NavigationRoute.Home
    } else {
        NavigationRoute.Onboarding
    }

    val goHome = remember(navController) {
        {
            val popped = navController.popBackStack(
                route = NavigationRoute.Home,
                inclusive = false
            )
            if (!popped) {
                navController.navigate(NavigationRoute.Home) {
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = start
    ) {
        composable<NavigationRoute.Onboarding> {
            Onboarding(
                onFinish = goHome,
                openDrawer = openDrawer,
            )
        }
        composable<NavigationRoute.Home> {
            HomeRoute(
                openDrawer = openDrawer,
                onNavigateCart = { navController.navigate(NavigationRoute.Cart) },
                onOpenDish = { id -> navController.navigate(NavigationRoute.MenuItemDetails(id)) },
                cartCount = cartCount,
            )
        }
        composable<NavigationRoute.Profile> {
            Profile(
                openDrawer = openDrawer,
                onLogout = {
                    navController.navigate(NavigationRoute.Onboarding) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
        composable<NavigationRoute.Cart> {
            CartScreen(
                openDrawer = openDrawer,
                onBack = { navController.navigateUp() },
                onCheckout = { }
            )
        }
        composable<NavigationRoute.MenuItemDetails> { entry ->
            val args = entry.toRoute<NavigationRoute.MenuItemDetails>()
            MenuItemDetails(
                id = args.dishId,
                openDrawer = openDrawer,
                onBack = { navController.navigateUp() },
                onOpenCart = { navController.navigate(NavigationRoute.Cart) },
                cartCount = cartCount
            )
        }
    }
}

