package com.example.flixfinder.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flixfinder.data.Cast
import com.example.flixfinder.data.RetrofitClient
import com.example.flixfinder.data.TvShowDetail
import com.example.flixfinder.data.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class TvShowDetailViewModel : ViewModel() {
    private val _tvShowDetail = MutableStateFlow<TvShowDetail?>(null)
    val tvShowDetail: StateFlow<TvShowDetail?> = _tvShowDetail.asStateFlow()

    private val _cast = MutableStateFlow<List<Cast>>(emptyList())
    val cast: StateFlow<List<Cast>> = _cast.asStateFlow()

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()

    fun getTvShowDetails(tvShowId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getTvShowDetails(tvShowId)
                _tvShowDetail.value = response
            } catch (e: Exception) {
                Log.e("TvShowDetailViewModel", "Error loading TV show details", e)
            }
        }
    }

    fun getTvShowCredits(tvShowId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getTvShowCredits(tvShowId)
                _cast.value = response.cast
            } catch (e: Exception) {
                Log.e("TvShowDetailViewModel", "Error loading TV show credits", e)
            }
        }
    }

    fun getTvShowVideos(tvShowId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getTvShowVideos(tvShowId)
                _videos.value = response.results
            } catch (e: Exception) {
                Log.e("TvShowDetailViewModel", "Error loading TV show videos", e)
            }
        }
    }
}