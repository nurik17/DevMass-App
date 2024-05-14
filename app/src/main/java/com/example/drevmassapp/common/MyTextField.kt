package com.example.drevmassapp.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.R
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.Coral1000
import com.example.drevmassapp.ui.theme.Gray600
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    onValueChanged: (String) -> Unit,
    leadingIcon: Int,
    showTrailingIcon: Boolean = false,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorIndicatorColor: Color = Coral1000,
    modifier: Modifier = Modifier,
) {

    var isFocused by remember { mutableStateOf(false) }

    var value by remember { mutableStateOf("") }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val colors = TextFieldDefaults.colors(
        focusedContainerColor = White,
        unfocusedContainerColor = White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = if(isFocused) Brand900 else Gray600,
        errorIndicatorColor = errorIndicatorColor
    )

    BasicTextField(
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
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        textStyle = typography.l17sfT400,
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                placeholder = {
                    Text(
                        text = hint,
                        style = typography.l17sfT400,
                        color = Gray600,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                innerTextField = {
                    innerTextField()
                },
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                trailingIcon = {
                    if (showTrailingIcon && isFocused) {
                        Image(
                            modifier = Modifier.clickable {
                                value = ""
                            },
                            painter = painterResource(id = R.drawable.ic_arounded_close),
                            contentDescription = "",
                        )
                    }
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = "",
                        tint = Brand900
                    )
                },
                contentPadding = PaddingValues(0.dp),
                colors = colors,
            )
        },
    )
}
