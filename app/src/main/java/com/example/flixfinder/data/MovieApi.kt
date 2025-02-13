package com.example.flixfinder.data.api

import com.example.flixfinder.data.model.MovieResponse
import com.example.flixfinder.data.model.TvShowResponse
import retrofit2.http.GET

interface MovieApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(): TvShowResponse
}