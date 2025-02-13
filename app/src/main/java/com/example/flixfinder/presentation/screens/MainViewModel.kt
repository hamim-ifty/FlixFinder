package com.example.flixfinder.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flixfinder.common.UiState
import com.example.flixfinder.data.api.RetrofitClient
import com.example.flixfinder.data.model.Movie
import com.example.flixfinder.data.model.TvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _movies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val movies: StateFlow<UiState<List<Movie>>> = _movies.asStateFlow()

    private val _tvShows = MutableStateFlow<UiState<List<TvShow>>>(UiState.Loading)
    val tvShows: StateFlow<UiState<List<TvShow>>> = _tvShows.asStateFlow()

    private val tag = "MainViewModel"

    init {
        Log.d(tag, "Initializing ViewModel")
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            Log.d(tag, "Starting API call for movies")
            _movies.value = UiState.Loading
            try {
                val response = RetrofitClient.api.getPopularMovies()
                Log.d(tag, "Movies API response received: ${response.results.size} items")
                if (response.results.isEmpty()) {
                    Log.w(tag, "API returned empty movie list")
                    _movies.value = UiState.Error("No movies found")
                } else {
                    _movies.value = UiState.Success(response.results)
                }
            } catch (e: Exception) {
                Log.e(tag, "Error loading movies", e)
                _movies.value = UiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }

    fun loadTvShows() {
        viewModelScope.launch {
            Log.d(tag, "Starting API call for TV shows")
            _tvShows.value = UiState.Loading
            try {
                val response = RetrofitClient.api.getPopularTvShows()
                Log.d(tag, "TV shows API response received: ${response.results.size} items")
                if (response.results.isEmpty()) {
                    Log.w(tag, "API returned empty TV show list")
                    _tvShows.value = UiState.Error("No TV shows found")
                } else {
                    _tvShows.value = UiState.Success(response.results)
                }
            } catch (e: Exception) {
                Log.e(tag, "Error loading TV shows", e)
                _tvShows.value = UiState.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}