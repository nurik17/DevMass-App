package com.example.drevmassapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.ui.theme.Brand400

@Composable
fun ShimmerBox(
    height: Dp,
    width: Dp,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = Brand400,
                shape = RoundedCornerShape(16.dp)
            )
            .shimmerEffect()
    )
}