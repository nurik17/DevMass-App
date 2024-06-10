package com.example.drevmassapp.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.R
import com.example.drevmassapp.ui.theme.Brand900
import com.example.drevmassapp.ui.theme.CoralRed1000
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray600
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    onValueChanged: (String) -> Unit,
    leadingIcon: Int,
    hint: String,
    value: String,
    isError: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    var showPasswordValue by remember { mutableStateOf(value = false) }

    val colors = TextFieldDefaults.colors(
        focusedContainerColor = White,
        unfocusedContainerColor = White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = if (isFocused) Brand900 else Gray600,
        cursorColor = Dark900,
        errorIndicatorColor = CoralRed1000
    )

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .indicatorLine(
                enabled = true,
                isError = isError,
                colors = colors,
                interactionSource = interactionSource,
                focusedIndicatorLineThickness = 3.dp,
                unfocusedIndicatorLineThickness = 2.dp
            )
            .onFocusChanged { isFocused = it.isFocused },
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        visualTransformation = if (showPasswordValue) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.sf_protext_semibold)),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Dark1000
        ),
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
                interactionSource = interactionSource,
                trailingIcon = {
                    if (showPasswordValue) {
                        IconButton(onClick = { showPasswordValue = false }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_not_glass),
                                contentDescription = "show_password"
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
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = "",
                        tint = if(isError) CoralRed1000 else Brand900
                    )
                },
                contentPadding = PaddingValues(0.dp),
                colors = colors,
                visualTransformation = if (showPasswordValue) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            )
        },
    )
}
