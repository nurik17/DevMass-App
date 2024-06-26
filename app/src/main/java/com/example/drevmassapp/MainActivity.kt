package com.example.drevmassapp

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.drevmassapp.navigation.MainNavGraph
import com.example.drevmassapp.ui.theme.DrevMassAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            DrevMassAppTheme {
                Box(modifier = Modifier.padding(bottom = 15.dp)) {
                    MainNavGraph()
                }
                ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
                    window2(view, insets)
                }
            }
        }
    }
}

private fun window2(view: View, windowInsets: WindowInsetsCompat): WindowInsetsCompat {
    val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

    val layoutParams = view.layoutParams
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        layoutParams.leftMargin = insets.left
        layoutParams.bottomMargin = insets.bottom
        layoutParams.rightMargin = insets.right
        view.layoutParams = layoutParams
    }

    return WindowInsetsCompat.CONSUMED
}
