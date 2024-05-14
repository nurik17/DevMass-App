package com.example.drevmassapp.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.typography

@Composable
fun CustomButton(
    text: String,
    isLoading: Boolean = false,
    textColor: Color = Color.White,
    backgroundColor: Color = Brand900,
    borderColor: Color = Brand900,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(50.dp)
    Box(
        modifier = modifier
            .background(backgroundColor, shape = shape)
            .border(2.dp, borderColor,shape = shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White
            )
        } else {
            Text(
                text = text,
                color = textColor,
            )
        }
    }
}