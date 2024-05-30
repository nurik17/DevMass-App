package com.example.drevmassapp.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.GrayDefault
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordTextField(
    value: String,
    label: String,
    onValueChanged: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var text by remember { mutableStateOf(value) }
    var showPasswordValue by remember { mutableStateOf(value = false) }


    val colors = TextFieldDefaults.colors(
        unfocusedLabelColor = Gray700,
        focusedLabelColor = Brand900,
        cursorColor = Dark1000,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = if (isFocused) Brand900 else GrayDefault,
        unfocusedIndicatorColor = if (isFocused) Brand900 else GrayDefault,
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
            )
            .onFocusChanged { isFocused = it.isFocused },
        value = text,
        onValueChange = {
            text = it
            onValueChanged(it)
        },
        label = {
            Text(
                text = label,
                style = typography.l13sf500,
            )
        },
        colors = colors,
        trailingIcon = {
            if (showPasswordValue) {
                IconButton(onClick = { showPasswordValue = false }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_not_glass),
                        contentDescription = "show_password",

                    )
                }
            } else {
                IconButton(onClick = { showPasswordValue = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_glass),
                        contentDescription = "hide_password"
                    )
                }
            }
        },

        shape = CutCornerShape(14.dp)
    )
}