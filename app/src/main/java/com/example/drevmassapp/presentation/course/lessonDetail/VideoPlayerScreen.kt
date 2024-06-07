package com.example.drevmassapp.presentation.course.lessonDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun VideoPlayerScreen(
    link: String
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.TopStart
    ) {
        YoutubePlayer(
            modifier = Modifier
                .fillMaxSize(),
            videoId = link
        )
    }
}