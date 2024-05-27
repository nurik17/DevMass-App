package com.example.drevmassapp.common

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@Composable
fun SetEdgeToEdge(
    lightColor: Color,
    darkColor: Color,
) {
    val context = LocalContext.current as ComponentActivity
    val isDarkMode = isSystemInDarkTheme()

    DisposableEffect(isDarkMode) {
        context.enableEdgeToEdge(
            statusBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    lightColor.toArgb(),
                    lightColor.toArgb()
                )
            } else {
                SystemBarStyle.dark(
                    darkColor.toArgb()
                )
            },
            /*navigationBarStyle = if(!isDarkMode){
                SystemBarStyle.light(
                    navigationBarLight,
                    navigationBarDark
                )
            } else {
                SystemBarStyle.dark(navigationBarDark)
            }*/
        )
        onDispose { }
    }
}