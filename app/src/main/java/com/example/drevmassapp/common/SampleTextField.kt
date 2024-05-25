package com.example.drevmassapp.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.GrayDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleTextField(
    value: String = "",
    label: String = "",
    onValueChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val colors = TextFieldDefaults.colors(
        unfocusedLabelColor = Gray700,
        focusedLabelColor = Gray700,
        cursorColor = Dark1000,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = GrayDefault,
        unfocusedIndicatorColor = GrayDefault,
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .indicatorLine(
                enabled = true,
                isError = false,
                colors = colors,
                interactionSource = interactionSource,
                focusedIndicatorLineThickness = 2.dp,
                unfocusedIndicatorLineThickness = 2.dp
            ) ,
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        label = { Text(text = label) },
        colors = colors,
        shape = CutCornerShape(14.dp)
    )
}