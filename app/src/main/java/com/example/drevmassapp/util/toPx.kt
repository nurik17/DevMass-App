package com.example.drevmassapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx(): Float {
    return LocalDensity.current.run {
        this@toPx.toPx()
    }
}