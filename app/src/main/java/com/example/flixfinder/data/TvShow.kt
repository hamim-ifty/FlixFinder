package com.example.flixfinder.data.model

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val first_air_date: String,
    val vote_average: Double
)

data class TvShowResponse(
    val page: Int,
    val results: List<TvShow>
)