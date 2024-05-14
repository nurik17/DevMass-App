package com.example.drevmassapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.R

data class Typographies(
    val l28sfD700: TextStyle,
    val l17sfT400: TextStyle,
    val l15sfT600:TextStyle,
)

val typography = Typographies(
    l28sfD700 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_bold)),
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Dark10000
    ),
    l17sfT400 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_protext_regular)),
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal,
        color = Gray800
    ),
    l15sfT600 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_protext_semibold)),
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Dark900
    )
)