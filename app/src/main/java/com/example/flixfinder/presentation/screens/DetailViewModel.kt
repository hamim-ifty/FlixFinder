package com.example.flixfinder.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flixfinder.data.RetrofitClient
import com.example.flixfinder.data.MovieDetail
import com.example.flixfinder.data.Cast
import com.example.flixfinder.data.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail: StateFlow<MovieDetail?> = _movieDetail.asStateFlow()

    private val _cast = MutableStateFlow<List<Cast>>(emptyList())
    val cast: StateFlow<List<Cast>> = _cast.asStateFlow()

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getMovieDetails(movieId)
                _movieDetail.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getMovieCredits(movieId)
                _cast.value = response.cast
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getMovieVideos(movieId)
                _videos.value = response.results
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}