package com.example.flixfinder.presentation.screens.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.flixfinder.data.Movie      // Add correct import
import com.example.flixfinder.data.TvShow     // Add correct import

@Composable
fun MediaGrid(
    items: List<Any>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(items) { item ->
            when (item) {
                is Movie -> MediaCard(
                    title = item.title,
                    posterPath = item.poster_path,
                    onClick = { onItemClick(item.id) }
                )
                is TvShow -> MediaCard(
                    title = item.name,
                    posterPath = item.poster_path,
                    onClick = { onItemClick(item.id) }
                )
            }
        }
    }
}