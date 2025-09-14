package com.example.lttle_lemon_app.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.lttle_lemon_app.AppDatabase
import com.example.lttle_lemon_app.MenuItemDao
import com.example.lttle_lemon_app.core.data.cart.CartRepositoryImpl
import com.example.lttle_lemon_app.core.domain.cart.CartRepository
import com.example.lttle_lemon_app.screens.cartScreen.CartViewModel
import com.example.lttle_lemon_app.screens.home.HomeViewModel
import com.example.lttle_lemon_app.screens.onboarding.OnboardingViewModel
import com.example.lttle_lemon_app.screens.profile.ProfileViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    // DB
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "menuDatabase"
        ).build()
    }
    single<MenuItemDao> { get<AppDatabase>().menuItemDao() }

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }
    }

    // Prefs
    single<SharedPreferences> {
        androidContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)
    }
    single<CartRepository> { CartRepositoryImpl() }

    viewModel { HomeViewModel(get()) }
    viewModel { CartViewModel(get<CartRepository>()) }
    viewModel { OnboardingViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}