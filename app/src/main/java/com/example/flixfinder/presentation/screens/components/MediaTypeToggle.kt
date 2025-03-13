package com.example.flixfinder.presentation.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flixfinder.ui.theme.NetflixRed
import com.example.flixfinder.ui.theme.Black
import com.example.flixfinder.ui.theme.White

@Composable
fun MediaTypeToggle(
    isMovieSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = { onToggle(true) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isMovieSelected) NetflixRed else Black,
                contentColor = White
            )
        ) {
            Text("Movies")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { onToggle(false) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isMovieSelected) NetflixRed else Black,
                contentColor = White
            )
        ) {
            Text("TV Shows")
        }
    }
}