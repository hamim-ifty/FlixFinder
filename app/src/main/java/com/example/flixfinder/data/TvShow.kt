package com.example.flixfinder.data

// Base TV Show model
data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val first_air_date: String,
    val vote_average: Double
)

// Detailed TV Show model
data class TvShowDetail(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val first_air_date: String,
    val number_of_seasons: Int?
)

// Response models
data class TvShowResponse(
    val page: Int,
    val results: List<TvShow>
)

data class TvCastResponse(
    val cast: List<Cast>
)

data class TvVideoResponse(
    val results: List<Video>
)