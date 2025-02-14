package com.example.flixfinder.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flixfinder.common.UiState
import com.example.flixfinder.presentation.screens.components.MediaGrid
import com.example.flixfinder.presentation.screens.components.MediaTypeToggle
import com.example.flixfinder.data.Movie
import com.example.flixfinder.data.TvShow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMovieDetail: (Int) -> Unit,
    onNavigateToTvDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    var isMovieSelected by remember { mutableStateOf(true) }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MediaTypeToggle(
                isMovieSelected = isMovieSelected,
                onToggle = { selected ->
                    isMovieSelected = selected
                    if (selected) viewModel.loadMovies() else viewModel.loadTvShows()
                }
            )

            when (val state = if (isMovieSelected)
                viewModel.movies.collectAsState().value
            else
                viewModel.tvShows.collectAsState().value) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    MediaGrid(
                        items = state.data,
                        onItemClick = { id ->
                            when (state.data.firstOrNull()) {
                                is Movie -> onNavigateToMovieDetail(id)
                                is TvShow -> onNavigateToTvDetail(id)
                                else -> {}
                            }
                        }
                    )
                }
                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Error: ${state.message}",
                                modifier = Modifier.padding(16.dp)
                            )
                            Button(
                                onClick = {
                                    if (isMovieSelected) viewModel.loadMovies()
                                    else viewModel.loadTvShows()
                                }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}