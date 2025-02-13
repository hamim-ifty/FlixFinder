package com.example.flixfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flixfinder.presentation.screens.DetailScreen
import com.example.flixfinder.presentation.screens.HomeScreen
import com.example.flixfinder.ui.theme.FlixFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlixFinderTheme {
                FlixFinderApp()
            }
        }
    }
}

@Composable
fun FlixFinderApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            "detail/{mediaId}",
            arguments = listOf(navArgument("mediaId") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailScreen(
                mediaId = backStackEntry.arguments?.getInt("mediaId") ?: 0,
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}