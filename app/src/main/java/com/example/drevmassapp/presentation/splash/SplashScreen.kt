package com.example.drevmassapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drevmassapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToOnBoardingScreen: () -> Unit = {}
) {
    var splashScreenFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(3000)
        splashScreenFinished = true
        navigateToOnBoardingScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(id = R.drawable.splash_background),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        if (!splashScreenFinished) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .width(61.dp)
                        .height(53.dp),
                    painter = painterResource(id = R.drawable.ic_logo_app),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .width(182.dp)
                        .height(28.dp),
                    painter = painterResource(id = R.drawable.ic_logo_name),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
fun Prev() {
    SplashScreen()
}