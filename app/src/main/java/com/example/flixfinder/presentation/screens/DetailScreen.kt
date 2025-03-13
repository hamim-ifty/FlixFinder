package com.example.flixfinder.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBackPress: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val movieDetail by viewModel.movieDetail.collectAsState()
    val cast by viewModel.cast.collectAsState()
    val videos by viewModel.videos.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId)
        viewModel.getMovieCredits(movieId)
        viewModel.getMovieVideos(movieId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movieDetail?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Backdrop Image
            item {
                movieDetail?.let { detail ->
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${detail.backdrop_path}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Rating
            item {
                movieDetail?.let { detail ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rating: ${detail.vote_average}/10",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // Overview
            item {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Text(
                    text = movieDetail?.overview ?: "",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Cast
            if (cast.isNotEmpty()) {
                item {
                    Text(
                        text = "Cast",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cast) { castMember ->
                            Card(
                                modifier = Modifier.width(120.dp)
                            ) {
                                Column {
                                    AsyncImage(
                                        model = "https://image.tmdb.org/t/p/w200${castMember.profile_path}",
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = castMember.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        text = castMember.character,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Trailers
            val youtubeTrailers = videos.filter { it.type == "Trailer" && it.site == "YouTube" }
            if (youtubeTrailers.isNotEmpty()) {
                item {
                    Text(
                        text = "Trailers",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                items(youtubeTrailers) { video ->
                    ListItem(
                        headlineContent = { Text(video.name) },
                        leadingContent = {
                            Icon(Icons.Filled.PlayArrow, contentDescription = "Play Trailer")
                        },
                        modifier = Modifier.clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.youtube.com/watch?v=${video.key}")
                            )
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}