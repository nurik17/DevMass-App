package com.example.drevmassapp.common

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun TemporarilyChangeStatusBarColor(
    temporaryColor: Color,
    defaultColor: Color,
    durationMillis: Long
) {
    val context = LocalContext.current as ComponentActivity

    LaunchedEffect(Unit) {
        context.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(temporaryColor.toArgb(), temporaryColor.toArgb())
        )

        delay(durationMillis)

        context.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(defaultColor.toArgb(), defaultColor.toArgb())
        )
    }
}