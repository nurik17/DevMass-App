package com.example.drevmassapp.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset


@Composable
fun DashedLine() {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
    ) {
        drawLine(
            color = Color(0xFFD6D1CE),
            start = Offset(0f, 0f),
            end = Offset(size.width, 10f),
            pathEffect = pathEffect
        )
    }
}