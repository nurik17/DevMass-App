package com.example.drevmassapp.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.R
import com.example.drevmassapp.ui.theme.Dark1000
import com.example.drevmassapp.ui.theme.Gray600
import com.example.drevmassapp.ui.theme.Gray700
import com.example.drevmassapp.ui.theme.GrayDefault
import com.example.drevmassapp.ui.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HintTextField(
    value: String = "",
    placeholder: String = "",
    onValueChanged: (String) -> Unit = {},
    focusedIndicatorColor: Color = GrayDefault,
    unfocusedIndicatorColor: Color = GrayDefault,
    keyboardType: KeyboardType = KeyboardType.Text,
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
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
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
            ),
        value = value,
        onValueChange = {
            onValueChanged(it)
        },
        colors = colors,
        shape = CutCornerShape(14.dp),
        placeholder = {
            Text(
                text = placeholder, style = typography.l17sfT400,
                color = Gray600
            )
        },
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.sf_protext_semibold)),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = Dark1000
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}