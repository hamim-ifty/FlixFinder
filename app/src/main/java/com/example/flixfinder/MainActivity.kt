package com.example.flixfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flixfinder.presentation.screens.DetailScreen
import com.example.flixfinder.presentation.screens.HomeScreen
import com.example.flixfinder.presentation.screens.TvShowDetailScreen
import com.example.flixfinder.ui.theme.FlixFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlixFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlixFinderApp()
                }
            }
        }
    }
}

@Composable
fun FlixFinderApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToMovieDetail = { movieId ->
                    navController.navigate("movie/$movieId")
                },
                onNavigateToTvDetail = { tvId ->
                    navController.navigate("tv/$tvId")
                }
            )
        }

        // Movie details route
        composable(
            route = "movie/{movieId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            DetailScreen(
                movieId = backStackEntry.arguments?.getInt("movieId") ?: 0,
                onBackPress = { navController.popBackStack() }
            )
        }

        // TV Show details route
        composable(
            route = "tv/{tvId}",
            arguments = listOf(
                navArgument("tvId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            TvShowDetailScreen(
                tvShowId = backStackEntry.arguments?.getInt("tvId") ?: 0,
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}