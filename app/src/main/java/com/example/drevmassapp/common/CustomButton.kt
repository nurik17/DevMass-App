package com.example.drevmassapp.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.ui.theme.Brand900

@Composable
fun CustomButton(
    text: String,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    textColor: Color = Color.White,
    backgroundColor: Color = Brand900,
    borderColor: Color = Brand900,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(50.dp)

    Button(
        modifier = modifier,
        onClick = { onButtonClick() },
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = backgroundColor,
            disabledContentColor = Color.White
        ),
        shape = shape,
        enabled = enabled,
        border = BorderStroke(2.dp,borderColor)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                color = textColor,
            )
        }
    }

    /*Box(
        modifier = modifier
            .background(backgroundColor, shape = shape)
            .border(2.dp, borderColor,shape = shape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                color = textColor,
            )
        }
    }*/
}