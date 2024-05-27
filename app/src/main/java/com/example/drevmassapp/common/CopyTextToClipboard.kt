package com.example.drevmassapp.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString

@Composable
fun CopyTextToClipboard(text: String) {
    val clipboardManager = LocalClipboardManager.current
    clipboardManager.setText(AnnotatedString(text))
}