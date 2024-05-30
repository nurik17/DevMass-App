package com.example.drevmassapp.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackBar(
    @DrawableRes drawableRes: Int,
    message: String,
    isRtl: Boolean = false,
    containerColor: Color = Color.White,
) {
    val shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
    Snackbar(containerColor = containerColor, shape = shape) {
        CompositionLocalProvider(
            LocalLayoutDirection provides
                    if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = drawableRes),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = message,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.White
                )
            }
        }
    }
}