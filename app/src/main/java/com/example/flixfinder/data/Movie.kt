package com.example.flixfinder.data

// Movie models
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val vote_average: Double
)

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val release_date: String
)

// Cast and Credits
data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String?
)

// Video
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)

// API Response models
data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)

data class CastResponse(
    val cast: List<Cast>
)

data class VideoResponse(
    val results: List<Video>
)