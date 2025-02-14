package com.example.flixfinder.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flixfinder.data.RetrofitClient  // Add correct import
import com.example.flixfinder.data.Movie           // Add correct import
import com.example.flixfinder.data.TvShow          // Add correct import
import com.example.flixfinder.common.UiState       // Add UiState import
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class MainViewModel : ViewModel() {
    private val _movies = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val movies: StateFlow<UiState<List<Movie>>> = _movies.asStateFlow()

    private val _tvShows = MutableStateFlow<UiState<List<TvShow>>>(UiState.Loading)
    val tvShows: StateFlow<UiState<List<TvShow>>> = _tvShows.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _movies.value = UiState.Loading
            try {
                val response = RetrofitClient.api.getPopularMovies()
                _movies.value = UiState.Success(response.results)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading movies", e)
                _movies.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadTvShows() {
        viewModelScope.launch {
            _tvShows.value = UiState.Loading
            try {
                val response = RetrofitClient.api.getPopularTvShows()
                _tvShows.value = UiState.Success(response.results)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading TV shows", e)
                _tvShows.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}