package com.example.drevmassapp.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SnackbarBlock(
    snackState: SnackbarHostState,
    text: String,
    iconId: Int,
    backgroundColor: Color,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Top)
        ) {
            SnackbarHost(
                hostState = snackState,
            ) {
                CustomSnackBar(
                    drawableRes = iconId,
                    message = text,
                    isRtl = false,
                    containerColor = backgroundColor,
                )
            }
        }
    }
}