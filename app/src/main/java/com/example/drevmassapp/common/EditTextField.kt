package com.example.drevmassapp.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.R
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray600

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    value: String = "",
    label: String = "",
    onValueChanged: (String) -> Unit = {},
    showTrailingIcon: Boolean = false,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val colors = TextFieldDefaults.colors(
        unfocusedLabelColor = Brand900,
        focusedLabelColor = Brand900,
        cursorColor = Dark1000,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = if (isFocused) Brand900 else Gray600,
        unfocusedIndicatorColor = if (isFocused) Brand900 else Gray600,
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .indicatorLine(
                enabled = true,
                isError = isError,
                colors = colors,
                interactionSource = interactionSource,
                focusedIndicatorLineThickness = 2.dp,
                unfocusedIndicatorLineThickness = 2.dp
            )
            .onFocusChanged { isFocused = it.isFocused },
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        label = { Text(text = label) },
        colors = colors,
        trailingIcon = {
            if (showTrailingIcon && isFocused && value.isNotEmpty()) {
                Image(
                    modifier = Modifier.clickable {
                        onValueChanged("")
                    },
                    painter = painterResource(id = R.drawable.ic_arounded_close),
                    contentDescription = "",
                )
            }
        },
        shape = CutCornerShape(14.dp)
    )
}