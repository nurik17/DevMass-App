package com.example.drevmassapp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.drevmassapp.R

data class Typographies(
    val l28sfD700: TextStyle,
    val l17sfT400: TextStyle,
    val l15sfT600: TextStyle,
    val l13sf500: TextStyle,
    val l15sf700: TextStyle,
    val l20sfD600: TextStyle
)

val typography = Typographies(
    l28sfD700 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_bold)),
        fontSize = 28.sp,
        color = Dark1000
    ),
    l17sfT400 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_protext_regular)),
        fontSize = 17.sp,
        color = Gray800
    ),
    l15sfT600 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_protext_semibold)),
        fontSize = 15.sp,
        color = Dark900
    ),
    l13sf500 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_text_medium)),
        fontSize = 13.sp,
        color = Brand900
    ),
    l15sf700 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_protext_bold)),
        fontSize = 15.sp,
        color = Dark900
    ),
    l20sfD600 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_semibold)),
        fontSize = 20.sp,
        color = Dark900
        )
)